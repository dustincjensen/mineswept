package events;

public class AboutEvent {
    public String getTitle() {
        return "About";
    }

    public String getMessage() {
        return "<html>" + getVersion() + "<br/>Dustin Jensen<br/>Created 2012-2020</html>";
    }
    
    private String getVersion() {
        return "MineSwept " + getClass().getPackage().getSpecificationVersion();        
    }
}