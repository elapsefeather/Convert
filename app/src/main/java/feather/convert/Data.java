package feather.convert;

/**
 * Created by feather on 2017/6/20.
 */

public class Data {

    String Name;
    float Exrate;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        if(!Name.trim().equals("USD"))
        {//除了美金外 其他取代掉轉換碼
            Name = Name.replace("USD", "");
        }
        this.Name = Name;
    }

    public float getExrate() {
        return Exrate;
    }

    public void setExrate(float Exrate) {
        this.Exrate = Exrate;
    }

}
