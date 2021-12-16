package Users;

import BotLogic.RegionStore;

import java.util.HashMap;
import java.util.Objects;

public class ScoreCounter {

    private final RegionStore regionStore;
    private String region;
    public int score;
    public HashMap<String, Integer> statistics;

    public ScoreCounter() {
        regionStore = new RegionStore();
        statistics = new HashMap<>();
        score = 0;
        initializeBestScoreHashMap();
    }

    public void add() {
        score++;
    }

    public int resetScore() {
        var s = score;
        score = 0;
        return s;
    }

    public void setRegion(String region){
        if(!Objects.equals(region, ""))
            this.region =  region;
        else
            this.region = "Страна по описанию";
    }

    public void updateStat() {
        if (statistics.get(region) < score)
            statistics.put(region, score);
    }

    public String getStat() {
        StringBuilder sb = new StringBuilder();

        for (String key : statistics.keySet()) {
            sb.append(String.format("%s: %d\n", key, statistics.get(key)));
        }
        return sb.toString();
    }

    private void initializeBestScoreHashMap(){
        for (String s : regionStore.regions) {
            statistics.put(s, 0);
        }
        statistics.put("Страна по описанию", 0);
    }
}
