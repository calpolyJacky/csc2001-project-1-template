public class Session {
    public int id;
    public String title;
    public String mentor;
    public String date;
    public String location;
    public int maxParticipants;

    public Session(int id, String title, String mentor, String date, String location, int maxParticipants) {
        this.id = id;
        this.title = title;
        this.mentor = mentor;
        this.date = date;
        this.location = location;
        this.maxParticipants = maxParticipants;

    }

    public String toString() {
        return "Session info: " + date + id + title + mentor + location + maxParticipants;
    }
}
