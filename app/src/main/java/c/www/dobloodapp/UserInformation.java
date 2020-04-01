package c.www.dobloodapp;

public class UserInformation {
    String Name;
    String bloodgroup;
    String gender;
    String dob;

    UserInformation() {
    }

    public UserInformation(String n,String bg, String ge, String db) {
        Name=n;
        bloodgroup = bg;
        gender = ge;
        dob = db;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public void setGe(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getName() {
        return Name;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }
}
