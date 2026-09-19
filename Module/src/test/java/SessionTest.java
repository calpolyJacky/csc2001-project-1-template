import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionTest {

    @Test
    void constructorStoresSessionDetailsAndStartsWithNoParticipants() {
        Session session = new Session(
                101,
                "Inclusive Leadership",
                "Morgan Lee",
                "2026-10-15",
                "Room 204",
                3
        );

        assertEquals(101, session.id);
        assertEquals("Inclusive Leadership", session.title);
        assertEquals("Morgan Lee", session.mentor);
        assertEquals("2026-10-15", session.date);
        assertEquals("Room 204", session.location);
        assertEquals(3, session.maxParticipants);
        assertEquals(0, session.participants);
    }

    @Test
    void registerParticipantIncrementsCountUntilSessionIsFull() {
        Session session = new Session(
                102,
                "Career Planning",
                "Alex Kim",
                "2026-10-16",
                "Room 101",
                2
        );

        assertTrue(session.registerParticipant());
        assertEquals(1, session.participants);

        assertTrue(session.registerParticipant());
        assertEquals(2, session.participants);

        assertFalse(session.registerParticipant());
        assertEquals(2, session.participants);
    }

    @Test
    void registerParticipantRejectsAZeroCapacitySession() {
        Session session = new Session(
                103,
                "Closed Session",
                "Sam Patel",
                "2026-10-17",
                "Online",
                0
        );

        assertFalse(session.registerParticipant());
        assertEquals(0, session.participants);
    }

    @Test
    void toStringIncludesEverySessionDetailAndParticipantCount() {
        Session session = new Session(
                104,
                "Mentoring Basics",
                "Jamie Chen",
                "2026-10-18",
                "Conference Room A",
                4
        );
        session.registerParticipant();

        assertEquals(
                "Session ID: 104"
                        + "\nTitle: Mentoring Basics"
                        + "\nMentor: Jamie Chen"
                        + "\nDate: 2026-10-18"
                        + "\nLocation: Conference Room A"
                        + "\nParticipants: 1/4",
                session.toString()
        );
    }
}
