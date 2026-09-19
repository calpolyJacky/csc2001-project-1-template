import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionListTest {

    @BeforeEach
    void resetList() {
        for (int id = 1; id <= 7; id++) {
            SessionList.removeById(id);
        }
    }

    @Test
    void emptyListReturnsEmptyOrMissingResults() {
        assertEquals(null, SessionList.findById(999));
        assertEquals("", SessionList.displayAll());
        assertEquals("", SessionList.findByMentor("Nobody"));
        assertEquals("", SessionList.findByTitle("Unknown"));
        assertEquals("", SessionList.findByDate("2099-01-01"));
        assertEquals("", SessionList.findByLocation("Nowhere"));
        assertEquals(false, SessionList.removeById(999));
        assertEquals(false, SessionList.registerById(999));
    }

    @Test
    void addSessionStoresSessionAndRejectsDuplicateIds() {
        Session original = session(1, "Original", "Avery", "2026-11-02", "Room A", 5);
        Session duplicate = session(1, "Duplicate", "Blake", "2026-11-01", "Room B", 8);

        assertEquals(true, SessionList.addSession(original));
        assertEquals(false, SessionList.addSession(duplicate));

        assertEquals(original, SessionList.findById(1));
        assertEquals(formatted(original), SessionList.displayAll());
    }

    @Test
    void addSessionOrdersSessionsByDate() {
        Session latest = session(1, "Latest", "Avery", "2026-12-12", "Room C", 5);
        Session earliest = session(2, "Earliest", "Blake", "2026-10-10", "Room A", 5);
        Session middle = session(3, "Middle", "Casey", "2026-11-11", "Room B", 5);

        SessionList.addSession(latest);
        SessionList.addSession(earliest);
        SessionList.addSession(middle);

        assertEquals(formatted(earliest, middle, latest), SessionList.displayAll());
    }

    @Test
    void findByMentorReturnsAllMatchesInDateOrder() {
        Session laterMatch = session(1, "Coaching", "Taylor", "2026-12-01", "Room A", 5);
        Session nonMatch = session(2, "Networking", "Jordan", "2026-11-01", "Room B", 5);
        Session earlierMatch = session(3, "Planning", "Taylor", "2026-10-01", "Room C", 5);
        addAll(laterMatch, nonMatch, earlierMatch);

        assertEquals(formatted(earlierMatch, laterMatch), SessionList.findByMentor("Taylor"));
        assertEquals("", SessionList.findByMentor("Unknown"));
    }

    @Test
    void findByTitleReturnsAllMatchesInDateOrder() {
        Session laterMatch = session(1, "Leadership", "Taylor", "2026-12-01", "Room A", 5);
        Session nonMatch = session(2, "Networking", "Jordan", "2026-11-01", "Room B", 5);
        Session earlierMatch = session(3, "Leadership", "Morgan", "2026-10-01", "Room C", 5);
        addAll(laterMatch, nonMatch, earlierMatch);

        assertEquals(formatted(earlierMatch, laterMatch), SessionList.findByTitle("Leadership"));
        assertEquals("", SessionList.findByTitle("Unknown"));
    }

    @Test
    void findByDateReturnsAllMatches() {
        Session firstMatch = session(1, "Coaching", "Taylor", "2026-11-01", "Room A", 5);
        Session secondMatch = session(2, "Networking", "Jordan", "2026-11-01", "Room B", 5);
        Session nonMatch = session(3, "Planning", "Morgan", "2026-12-01", "Room C", 5);
        addAll(firstMatch, secondMatch, nonMatch);

        assertEquals(formatted(firstMatch, secondMatch), SessionList.findByDate("2026-11-01"));
        assertEquals("", SessionList.findByDate("2099-01-01"));
    }

    @Test
    void findByLocationReturnsAllMatchesInDateOrder() {
        Session laterMatch = session(1, "Coaching", "Taylor", "2026-12-01", "Online", 5);
        Session nonMatch = session(2, "Networking", "Jordan", "2026-11-01", "Room B", 5);
        Session earlierMatch = session(3, "Planning", "Morgan", "2026-10-01", "Online", 5);
        addAll(laterMatch, nonMatch, earlierMatch);

        assertEquals(formatted(earlierMatch, laterMatch), SessionList.findByLocation("Online"));
        assertEquals("", SessionList.findByLocation("Unknown"));
    }

    @Test
    void removeByIdRemovesOnlyTheRequestedSession() {
        Session first = session(1, "First", "Avery", "2026-10-01", "Room A", 5);
        Session middle = session(2, "Middle", "Blake", "2026-11-01", "Room B", 5);
        Session last = session(3, "Last", "Casey", "2026-12-01", "Room C", 5);
        addAll(first, middle, last);

        assertEquals(true, SessionList.removeById(2));
        assertEquals(null, SessionList.findById(2));
        assertEquals(formatted(first, last), SessionList.displayAll());

        assertEquals(true, SessionList.removeById(1));
        assertEquals(formatted(last), SessionList.displayAll());

        assertEquals(true, SessionList.removeById(3));
        assertEquals("", SessionList.displayAll());
        assertEquals(false, SessionList.removeById(3));
    }

    @Test
    void registerByIdUpdatesTheMatchingSessionAndHonorsCapacity() {
        Session session = session(7, "Coaching", "Taylor", "2026-10-01", "Room A", 1);
        SessionList.addSession(session);

        assertEquals(true, SessionList.registerById(7));
        assertEquals(1, session.participants);

        assertEquals(false, SessionList.registerById(7));
        assertEquals(1, session.participants);
        assertEquals(false, SessionList.registerById(999));
    }

    private static Session session(int id, String title, String mentor, String date,
                                   String location, int maxParticipants) {
        return new Session(id, title, mentor, date, location, maxParticipants);
    }

    private static void addAll(Session... sessions) {
        for (Session session : sessions) {
            SessionList.addSession(session);
        }
    }

    private static String formatted(Session... sessions) {
        StringBuilder result = new StringBuilder();

        for (Session session : sessions) {
            result.append(session).append("\n\n");
        }

        return result.toString();
    }
}
