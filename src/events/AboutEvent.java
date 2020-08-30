package events;

public class AboutEvent {
    private static final String VERSION = "2.1.2";

    public String getTitle() {
        return "About";
    }

    public String getMessage() {
        return "<html>" + getVersion() + "<br/>Dustin Jensen<br/>Created 2012-2020</html>";
    }
    
    private String getVersion() {
        return "MineSwept " + VERSION;        
    }
}