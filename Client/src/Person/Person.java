package Person;

public class Person {
    private String NickName;
    private String Status;
    private int[] Pos;

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int[] getPos() {
        return Pos;
    }

    public void setPos(int[] pos) {
        Pos = pos;
    }

    public Person(String NickName, String Status, int[] Pos) {
        this.NickName = NickName;
        this.Pos = Pos;
        this.Status = Status;
    }

}
