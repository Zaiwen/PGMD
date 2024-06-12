package com.example.swinedatebaseproject.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("counter")
public class Counter {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("page")
    private String page;

    @TableField("count")
    private int count;

    public void setPage(String page) {
        this.page = page;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Counter counter = (Counter) o;

        if (count != counter.count) return false;
        if (!id.equals(counter.id)) return false;
        return page.equals(counter.page);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + page.hashCode();
        result = 31 * result + count;
        return result;
    }

    @Override
    public String toString() {

        String sb = getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", page=" + page +
                ", count=" + count +
                "]";
        return sb;
    }
}