package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.ParserDataDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellRangeAddress;

import static com.bitbybit.corebe.utils.Const.*;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.models.EventType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ParserService {
    // list of the merged cells in the sheet
    private static List<CellRangeAddress> mergedRegions;

    private void retrieveGroups(Map<Integer, List<Cell>> data,
                                       List<String> groups) {
        int curr = START;
        int col = 2;
        int i = 0;

        var groupRow = data.get(curr);

        // retrieve the groups
        for (;; col += 2) {
            Cell cell = groupRow.get(col);

            if (cell.getCellType() == CellType.STRING) {
                groups.add(cell.getStringCellValue());
            } else {
                // null cell
                //  --> no more groups
                break;
            }
        }
    }

    /**
     * check if the activity <name> is a course
     *
     * @param name - name of the activity
     */
    private static boolean isCourse(String name) {
        int i;

        for (i = 0; i < COURSE.size(); ++i) {
            if (name.contains(COURSE.get(i))) {
                return true;
            }
        }

        return false;
    }

    private Event getCourse(Cell cell, int day, int t) {
        Event c = new Event(cell.getStringCellValue(), EventType.CURS, HOURS.get(t),
                DAYS.get(day), 2);

        // check alignment to determine the parity
        var align = cell.getCellStyle().getAlignment();

        if (align == HorizontalAlignment.CENTER) {
            c.setParity(2);
        } else if (align == HorizontalAlignment.RIGHT) {
            c.setParity(0);
        } else {
            c.setParity(1);
        }

        return c;
    }

    /**
     * retrieve courses by day, time
     *
     * @param pos - starting position in the table
     *              for the first day
     */
    private void retrieveCourses(int pos,
                                        Map<Integer, List<Cell>> data,
                                        List<Event> courses) {
        int curr = pos;

        int numDays = DAYS.size();
        int i;

        for (i = 0; i < numDays; ++i, curr += ROWS_PER_DAY) {
            int t;

            for (t = 0; t < ROWS_PER_DAY; t += 2) {
                var row = data.get(curr + t);

                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.STRING &&
                            isCourse(cell.getStringCellValue())) {
//                        Course c = getCourse(cell, i, t >>> 1);
                        Event c = getCourse(cell, i, t >>> 1);
                        courses.add(c);
                    }
                }

                row = data.get(curr + t + 1);
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.STRING &&
                            isCourse(cell.getStringCellValue())) {
                        Event c = getCourse(cell, i, t >>> 1);
                        courses.add(c);
                    }
                }
            }
        }
    }

    private Event getLab(Cell cell, int day, int t, String group) {
        Event l;

        if (isSem(cell.getStringCellValue())) {
            l = new Event(cell.getStringCellValue(), EventType.SEMINAR, HOURS.get(t),
                    DAYS.get(day), 2);
        } else {
            l = new Event(cell.getStringCellValue(), EventType.LAB, HOURS.get(t),
                    DAYS.get(day), 2);
        }

        // check alignment to determine the parity
        var align = cell.getCellStyle().getAlignment();

        if (align == HorizontalAlignment.CENTER) {
            l.setParity(2);
        } else if (align == HorizontalAlignment.RIGHT) {
            l.setParity(0);
        } else if (align == HorizontalAlignment.GENERAL ||
                align == HorizontalAlignment.LEFT) {
            l.setParity(1);
        }

        return l;
    }

    /**
     * check if the activity <name> is a lab/seminar
     *
     * @param name - name of the activity
     */
    private boolean isLab(String name) {
        int i;

        for (i = 0; i < LAB_SEM.size(); ++i) {
            if (name.contains(LAB_SEM.get(i))) {
                return true;
            }
        }

        return false;
    }

    private boolean isSem(String name) {
        int i;

        for (i = 0; i < SEM.size(); ++i) {
            if (name.contains(SEM.get(i))) {
                return true;
            }
        }

        return false;
    }

    private boolean checkMergedCells(Cell cell) {
        var addr = cell.getAddress();

        int r = addr.getRow();
        int c = addr.getColumn();

        for (var reg : mergedRegions) {
            var rr = reg.getFirstRow();
            var cc = reg.getFirstColumn();

            if (r == rr && c == cc) {
                int lc = reg.getLastColumn();

                return (lc == c + 1);
            }
        }

        return false;
    }

    private void checkCell(Cell cell,
                                  int day,
                                  int t, // time
                                  String g, // group
                                  List<Map<Integer, Map<Integer, Event>>> activitiesA,
                                  List<Map<Integer, Map<Integer, Event>>> activitiesB) {
        if (cell.getCellType() == CellType.STRING &&
                isLab(cell.getStringCellValue())) {
            Event l = getLab(cell, day, t >>> 1, g);

            int par = l.getParity();
            if (par == 1) {
                activitiesA.get(day).get(1).put(t >>> 1, l);
                activitiesB.get(day).get(1).put(t >>> 1, l);
            } else if (par == 0) {
                activitiesA.get(day).get(0).put(t >>> 1, l);
                activitiesB.get(day).get(0).put(t >>> 1, l);
            } else {
                activitiesA.get(day).get(0).put(t >>> 1, l);
                activitiesA.get(day).get(1).put(t >>> 1, l);

                if (checkMergedCells(cell)) {
                    activitiesB.get(day).get(0).put(t >>> 1, l);
                    activitiesB.get(day).get(1).put(t >>> 1, l);
                }
            }
        }
    }

    /**
     * retrieve courses by day, time
     *
     * @param pos - starting position in the table
     *              for the first day
     */
    private void retrieveLabs(int pos,
                                     Map<Integer, List<Cell>> data,
                                     List<String> groups,
                                     Map<String, List<Map<Integer,
                                             Map<Integer, Event>>>> activities) {
        int col = GOFF;

        int groupCt = 0;
        for (var g : groups) {
            var ga = g + " " + "A";
            var gb = g + " " + "B";

            // get the activities from the map
            var activitiesA = activities.get(ga);
            var activitiesB = activities.get(gb);

            int i;
            int numDays = DAYS.size();

            int curr = pos;

            for (i = 0; i < numDays; ++i, curr += ROWS_PER_DAY) {
                int t;

                for (t = 0; t < ROWS_PER_DAY; t += 2) {
                    var row = data.get(curr + t);

                    Cell cell = row.get(groupCt + GOFF);
                    checkCell(cell, i, t, g, activitiesA, activitiesB);

                    cell = row.get(groupCt + GOFF + 1);
                    checkCell(cell, i, t, g, activitiesB, activitiesA);

                    row = data.get(curr + t + 1);

                    cell = row.get(groupCt + GOFF);
                    checkCell(cell, i, t, g, activitiesA, activitiesB);

                    cell = row.get(groupCt + GOFF + 1);
                    checkCell(cell, i, t, g, activitiesB, activitiesA);
                }
            }

            groupCt += 2;
        }
    }

    /**
     * add the courses to each group's schedule
     *
     * @param courses - list of all the courses
     * @param groups - list of the groups in the table
     * @param activities - activities per group -> course/lab
     */
    private void addCourses(List<Event> courses,
                                   List<String> groups,
                                   Map<String,
                                           List<Map<Integer,
                                                   Map<Integer, Event>>>> activities) {
        for (var g : groups) {
            for (var sgr = 'A'; sgr <= 'B'; ++sgr) {
                List<Map<Integer,
                        Map<Integer, Event>>> days = new ArrayList<>();

                int d;
                for (d = 0; d < DAYS.size(); ++d) {
                    Map<Integer, Map<Integer, Event>> par = new HashMap<>();
                    par.put(0, new HashMap<>());
                    par.put(1, new HashMap<>());

                    days.add(par);
                }

                activities.put(g + " " + sgr, days);

                int t;
                for (var c : courses) {
                    d = DAYS_MAP.get(c.getWeekday());
                    t = HOURS_MAP.get(c.getTimeslot());

                    int par = c.getParity();
                    if (par == 2) {
                        days.get(d).get(0).put(t, c);
                        days.get(d).get(1).put(t, c);
                    } else {
                        days.get(d).get(par).put(t, c);
                    }
                }
            }
        }
    }

    public List<Event> execParser(ParserDataDto parserData) throws IOException {
        /*
         * args[ ]
         *     [0] - orar
         *     [1] - seria -- ex. CA
         *     [2] - grupa -- nr
         *     [3] - semigrupa -- A/B
         */
        var userData = parserData.getUser();
        MultipartFile multipartFile = parserData.getFile();

        InputStream file = multipartFile.getInputStream();

//        String fName = "TODO";
//        FileInputStream file = new FileInputStream(new File(fName));

        // create Workbook instance holding reference to .xls file
        HSSFWorkbook workbook = new HSSFWorkbook(file);
        workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

        // get the first (0) sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);

        mergedRegions = sheet.getMergedRegions();

        /*
         * Collections needed
         * > data - raw data of the table (Cell)
         * > groups - group names
         * > courses - list of the courses
         *
         * >> activities - (<group>, [(<parity>,
         *                            (<time>, <course/lab>))])
         */
        Map<Integer, List<Cell>> data = new HashMap<>();
        List<String> groups = new ArrayList<>();
        List<Event> courses = new ArrayList<>();
        Map<String,
                List<Map<Integer,
                        Map<Integer, Event>>>> activities = new HashMap<>();

        int i = 0;

        // iterate through each row
        for (Row row : sheet) {
            data.put(i, new ArrayList<Cell>());

            int pos;

            for (pos = 0; pos < MAX_COLS; ++pos) {
                Cell cell = row.getCell(pos);

                data.get(i).add(cell);
            }

            ++i;
        }

        file.close();

        retrieveGroups(data, groups);

        int curr = START;

        // get the actual start of the schedule
        // - check if there is a semi-group row
        ++curr;
        var auxCell = data.get(curr).get(2).getStringCellValue();
        if (auxCell.startsWith(SGR)) {
            // jump over one more row
            ++curr;
        }
        ++curr;

        retrieveCourses(curr, data, courses);

        addCourses(courses, groups, activities);
        retrieveLabs(curr, data, groups, activities);

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        String seria = userData.getSeries();
        String grupa = userData.getGroup();
        String sgr = userData.getSemiGroup();

        String group = grupa + " " + seria;
        String sgrEntry = group + " " + sgr;

        var schedule = activities.get(sgrEntry);

        int d;

        List<Event> events = new ArrayList<>();
        for (d = 0; d < schedule.size(); ++d) {
            var dSchedule = schedule.get(d);

            var hours0 = dSchedule.get(0);

            for (var time : hours0.keySet()) {
                Event e = hours0.get(time);
                events.add(e);
            }

            var hours1 = dSchedule.get(1);

            for (var time : hours1.keySet()) {
                Event e = hours1.get(time);
                if (e.getParity() != 2) {
                    events.add(e);
                }
            }
        }

        return events;

//        mapper.writeValue(new File("eventList.json"), events);
    }
}

