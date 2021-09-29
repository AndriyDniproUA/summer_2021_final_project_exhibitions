package ua.dp.exhibitions.daoUtil;

import ua.dp.exhibitions.dao.ShowsDAO;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.exceptions.DaoException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



/**
 * ShowsDaoValidateShowRooms supports ShowDAO
 */
public class ShowsDaoValidateShowRooms {

    /**
     * validateShowRoomsAvailability() checks if selected rooms for a new show are available
     * returns a list of messages listing conflicts of unavailability
     */
    public static List<String> validateShowRoomsAvailability(Show show) throws DaoException {
        ShowsDAO showsDAO = ShowsDAO.getInstance();
        List<String> validationFeedback = new ArrayList<>();

        List<Show> existingShows = showsDAO.getAllShows();

        LocalDate newShowBegins = show.getDateBegins();
        LocalDate newShowEnds = show.getDateEnds();
        String[] newShowRooms = show.getRooms();

        for (Show existingShow : existingShows) {
            LocalDate exShowBegins = existingShow.getDateBegins();
            LocalDate exShowEnds = existingShow.getDateEnds();
            String[] exShowRooms = existingShow.getRooms();

            if (haveCommonRooms(newShowRooms, exShowRooms) &&
                    shareCommonDates(newShowBegins, newShowEnds, exShowBegins, exShowEnds)) {
                String message = "Conflict with exhibition " + existingShow.getSubject();
                validationFeedback.add(message);
            }
        }
        return validationFeedback;
    }

    /**
     * haveCommonRooms() checks if a new show has common rooms with existing shows
     */
    private static boolean haveCommonRooms(String[] newShowRooms, String[] exShowRooms) {
        for (String newShowRoom : newShowRooms) {
            for (String exShowRoom : exShowRooms) {
                if (newShowRoom.equals(exShowRoom)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * haveCommonRooms() checks if a new show has common dates with existing shows
     */
    private static boolean shareCommonDates(LocalDate nShowBeg, LocalDate nShowEnd,
                                     LocalDate exShowBeg, LocalDate exShowEnd) {

        /**
         * new show begins during existing show period
         */
        if (nShowBeg.isAfter(exShowBeg) && nShowBeg.isBefore(exShowEnd)) {
            return true;
        }

        /**
         * new show ends during existing show period
         */
        if (nShowEnd.isAfter(exShowBeg) && nShowEnd.isBefore(exShowEnd)) {
            return true;
        }

        /**
         * new show begins at the exact date with existing show beginning or end
         */

        if (nShowBeg.isEqual(exShowBeg) || nShowBeg.isEqual(exShowEnd)) {
            return true;
        }

        /**
         * new show ends at the exact date with existing show beginning or end
         */
        if (nShowEnd.isEqual(exShowBeg) || nShowEnd.isEqual(exShowEnd)) {
            return true;
        }
        return false;
    }


}
