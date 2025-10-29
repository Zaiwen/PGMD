package com.example.swinedatebaseproject.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "feed")
public class Feed implements Serializable {
    @TableId(value = "Feed_index")
    private Integer feedIndex;

    @TableField(value = "tag")
    private String tag;

    @TableField(value = "time")
    private Integer time;

    @TableField(value = "Nurseing_feed_percentage")
    private float nurseingFeedPercentage;

    @TableField(value = "Phase_1_feed_percentage")
    private float phase1FeedPercentage;

    @TableField(value = "Phase_2_feed_percentage")
    private float phase2FeedPercentage;

    @TableField(value = "Phase_3_feed_percentage")
    private float phase3FeedPercentage;

    @TableField(value = "day_of_intake")
    private float dayOfIntake;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feed feed = (Feed) o;

        if (Float.compare(feed.nurseingFeedPercentage, nurseingFeedPercentage) != 0) return false;
        if (Float.compare(feed.phase1FeedPercentage, phase1FeedPercentage) != 0) return false;
        if (Float.compare(feed.phase2FeedPercentage, phase2FeedPercentage) != 0) return false;
        if (Float.compare(feed.phase3FeedPercentage, phase3FeedPercentage) != 0) return false;
        if (Float.compare(feed.dayOfIntake, dayOfIntake) != 0) return false;
        if (!feedIndex.equals(feed.feedIndex)) return false;
        if (!tag.equals(feed.tag)) return false;
        return time.equals(feed.time);
    }

    @Override
    public int hashCode() {
        int result = feedIndex.hashCode();
        result = 31 * result + tag.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + (nurseingFeedPercentage != +0.0f ? Float.floatToIntBits(nurseingFeedPercentage) : 0);
        result = 31 * result + (phase1FeedPercentage != +0.0f ? Float.floatToIntBits(phase1FeedPercentage) : 0);
        result = 31 * result + (phase2FeedPercentage != +0.0f ? Float.floatToIntBits(phase2FeedPercentage) : 0);
        result = 31 * result + (phase3FeedPercentage != +0.0f ? Float.floatToIntBits(phase3FeedPercentage) : 0);
        result = 31 * result + (dayOfIntake != +0.0f ? Float.floatToIntBits(dayOfIntake) : 0);
        return result;
    }


    @Override
    public String toString() {
        String sb = getClass().getSimpleName() +
                "Feed{" +
                "feedIndex=" + feedIndex +
                ", tag='" + tag + '\'' +
                ", time=" + time +
                ", nurseingFeedPercentage=" + nurseingFeedPercentage +
                ", phase1FeedPercentage=" + phase1FeedPercentage +
                ", phase2FeedPercentage=" + phase2FeedPercentage +
                ", phase3FeedPercentage=" + phase3FeedPercentage +
                ", dayOfIntake=" + dayOfIntake +
                '}';
        return  sb;
    }
}
