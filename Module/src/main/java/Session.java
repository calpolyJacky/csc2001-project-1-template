public class Session {
    public int id;
    public String title;
    public String mentor;
    public String date;
    public String location;
    public int maxParticipants;
    public int participants;

    public Session(int id, String title, String mentor, String date,
                   String location, int maxParticipants) {
        this.id = id;
        this.title = title;
        this.mentor = mentor;
        this.date = date;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.participants = 0;
    }

    public boolean registerParticipant() {
        if (participants >= maxParticipants) {
            return false;
        }

        participants++;
        return true;
    }

    public String toString() {
        return "Session ID: " + id +
                "\nTitle: " + title +
                "\nMentor: " + mentor +
                "\nDate: " + date +
                "\nLocation: " + location +
                "\nParticipants: " + participants + "/" + maxParticipants;
    }
}
