package c.www.dobloodapp;

public class admins {
    String hospitalName;
    String hospitalId;
    String hospitalAdd;

    admins()
    {

    }
    public admins(String hn, String hid, String hadd)
    {
        hospitalName=hn;
        hospitalId=hid;
        hospitalAdd=hadd;
    }

    public void setName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public void setHospitalAdd(String hospitalAdd) {
        this.hospitalAdd = hospitalAdd;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public String getHospitalAdd() {
        return hospitalAdd;
    }
}
