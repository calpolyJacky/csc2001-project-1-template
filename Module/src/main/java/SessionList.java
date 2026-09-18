public class SessionList
{
    public static class Node
    {
        Session first;
        Node rest;

        Node(Session first, Node rest)
        {
            this.first = first;
            this.rest = rest;
        }
    }

    private static Node start = null;

    private static Session findById(Node x, int y)
    {
        if (x == null)
        {
            return null;
        }

        if (x.first.id == y)
        {
            return x.first;
        }

        return findById(x.rest, y);
    }

    public static Session findById(int y)
    {
        return findById(start, y);
    }

    private static Node addSession(Node x, Session y)
    {
        if (x == null)
        {
            return new Node(y, null);
        }

        if (y.date.compareTo(x.first.date) < 0)
        {
            return new Node(y, x);
        }

        x.rest = addSession(x.rest, y);

        return x;
    }

    public static boolean addSession(Session x)
    {
        if (findById(x.id) != null)
        {
            return false;
        }

        start = addSession(start, x);
        return true;
    }

    private static String displayAll(Node x)
    {
        if (x == null)
        {
            return "";
        }

        return x.first + "\n\n" + displayAll(x.rest);
    }

    public static String displayAll()
    {
        return displayAll(start);
    }

    private static String findByMentor(Node x, String y)
    {
        if (x == null)
        {
            return "";
        }

        if (x.first.mentor.equals(y))
        {
            return x.first + "\n\n" + findByMentor(x.rest, y);
        }

        return findByMentor(x.rest, y);
    }

    public static String findByMentor(String y)
    {
        return findByMentor(start, y);
    }

    private static String findByTitle(Node x, String y)
    {
        if (x == null)
        {
            return "";
        }

        if (x.first.title.equals(y))
        {
            return x.first + "\n\n" + findByTitle(x.rest, y);
        }

        return findByTitle(x.rest, y);
    }

    public static String findByTitle(String y)
    {
        return findByTitle(start, y);
    }

    private static String findByDate(Node x, String y)
    {
        if (x == null)
        {
            return "";
        }

        if (x.first.date.equals(y))
        {
            return x.first + "\n\n" + findByDate(x.rest, y);
        }

        return findByDate(x.rest, y);
    }

    public static String findByDate(String y)
    {
        return findByDate(start, y);
    }

    private static String findByLocation(Node x, String y)
    {
        if (x == null)
        {
            return "";
        }

        if (x.first.location.equals(y))
        {
            return x.first + "\n\n" + findByLocation(x.rest, y);
        }

        return findByLocation(x.rest, y);
    }

    public static String findByLocation(String y)
    {
        return findByLocation(start, y);
    }

    private static Node removeById(Node x, int y)
    {
        if (x == null)
        {
            return null;
        }

        if (x.first.id == y)
        {
            return x.rest;
        }

        x.rest = removeById(x.rest, y);

        return x;
    }

    public static boolean removeById(int x)
    {
        if (findById(x) == null)
        {
            return false;
        }

        start = removeById(start, x);

        return true;
    }

    public static boolean registerById(int y)
    {
        Session x = findById(y);

        if (x == null)
        {
            return false;
        }

        return x.registerParticipant();
    }
}
