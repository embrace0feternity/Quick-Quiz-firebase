package database;

public class User {
    private String mId, mName, mScore;

    public User(){

    }

    public User(String id, String name, String score){
        mId = id;
        mName = name;
        mScore = score;
    }


    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setScore(String mScore) {
        this.mScore = mScore;
    }

    public String getScore() {
        return mScore;
    }
}
