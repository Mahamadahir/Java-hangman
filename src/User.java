public class User {
    private int wins = 0;
    private int losses = 0;
    private String username;


    public User(String username){
        this.username=username;
    }
    public int getLosses() {
        return losses;
    }

    public int getWins() {
        return wins;
    }

    public String getUsername() {
        return username;
    }

        public void setUsername(String username) {
        this.username = username;
    }

    public void won(){
        this.wins++;
    }

    public void lost(){
        this.losses++;
    }
}
