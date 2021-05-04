package sg.edu.rp.id18044455.horoskopos;

public class Horoscope {

    private String horoscopeName;
    private String horoscopeDate;
    private int horoscopeImage;

    public Horoscope(String horoscopeName, String horoscopeDate, int horoscopeImage) {
        this.horoscopeName = horoscopeName;
        this.horoscopeDate = horoscopeDate;
        this.horoscopeImage = horoscopeImage;
    }

    public String getHoroscopeName() {
        return horoscopeName;
    }

    public void setHoroscopeName(String horoscopeName) {
        this.horoscopeName = horoscopeName;
    }

    public String getHoroscopeDate() {
        return horoscopeDate;
    }

    public void setHoroscopeDate(String horoscopeDate) {
        this.horoscopeDate = horoscopeDate;
    }

    public int getHoroscopeImage() {
        return horoscopeImage;
    }

    public void setHoroscopeImage(int horoscopeImage) {
        this.horoscopeImage = horoscopeImage;
    }
}//end of class
