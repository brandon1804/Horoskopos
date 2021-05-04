package sg.edu.rp.id18044455.horoskopos;

public class Zodiac {
    private String zodiacName;
    private String zodiacYears;
    private int zodiacImage;

    public Zodiac(String zodiacName, String zodiacYears, int zodiacImage) {
        this.zodiacName = zodiacName;
        this.zodiacYears = zodiacYears;
        this.zodiacImage = zodiacImage;
    }

    public String getZodiacName() {
        return zodiacName;
    }

    public void setZodiacName(String zodiacName) {
        this.zodiacName = zodiacName;
    }

    public String getZodiacYears() {
        return zodiacYears;
    }

    public void setZodiacYears(String zodiacYears) {
        this.zodiacYears = zodiacYears;
    }

    public int getZodiacImage() {
        return zodiacImage;
    }

    public void setZodiacImage(int zodiacImage) {
        this.zodiacImage = zodiacImage;
    }


}//end of class
