package de.jannnnek.planetinc.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScoreboardBuilder {

    public static HashMap<String, ScoreboardBuilder> scoreboards = new HashMap<>();

    private Scoreboard scoreboard;
    private Objective objective;
    private String title;
    private List<Score> scores;

    public ScoreboardBuilder() {
        scores = new ArrayList<>();
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("dummy", "dummy");
    }

    public ScoreboardBuilder(String title, List<Score> scores) {
        this.title = title;
        this.scores = scores;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("dummy", "dummy");
    }

    public ScoreboardBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ScoreboardBuilder addScore(String name, int count) {
        this.objective.getScore(name).setScore(count);
        return this;
    }

    public List<Score> getScores() {
        return this.scores;
    }

    public String getTitle() {
        return this.title;
    }

    public ScoreboardBuilder removeScore(Score score) {
        this.scores.remove(score);
        return this;
    }

    public void updateScoreboard(String oldScore, String newScore) {
        int index = objective.getScore(oldScore).getScore();
        scoreboard.resetScores(oldScore);
        objective.getScore(newScore).setScore(index);
    }
    public void updateScoreboardWithIdentifier(String identifier, String newScore) {
        Score score = null;
        for (String entry : scoreboard.getEntries()) {
            if(entry.startsWith(identifier)) score = objective.getScore(entry);
        }
        int index = score.getScore();
        scoreboard.resetScores(score.getEntry());
        objective.getScore(newScore).setScore(index);
    }

    public void apply(Player p) {
        Team team = scoreboard.registerNewTeam("collision");
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        team.addEntry(p.getName());
        scoreboards.put(p.getName(), this);
        objective.setDisplayName(getTitle());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        p.setScoreboard(scoreboard);
    }

    public void removeExisting(Player p) {
        scoreboards.remove(p, this);
        p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    public static ScoreboardBuilder getScoreboard(String playername) {
        return scoreboards.get(playername);
    }

}
