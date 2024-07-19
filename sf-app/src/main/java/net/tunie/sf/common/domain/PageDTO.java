package net.tunie.sf.common.domain;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T> {

    private long total;

    private long pages;

    private long size;

    private long num;

    private List<T> records;

}
