package com.backjoongwon.cvi.dto;

import java.util.List;

public class VaccineParserResponse {

    private long currentCount;
    private List<DataByRegion> data;
    private long matchCount;
    private long page;
    private long perPage;
    private long totalCount;

    public long getCurrentCount() {
        return currentCount;
    }

    public List<DataByRegion> getData() {
        return data;
    }

    public long getMatchCount() {
        return matchCount;
    }

    public long getPage() {
        return page;
    }

    public long getPerPage() {
        return perPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    private static class DataByRegion{
        private long accumulatedFirstCnt;
        private long accumulatedSecondCnt;
        private String baseDate;
        private long firstCnt;
        private long secondCnt;
        private String sido;
        private long totalFirstCnt;
        private long totalSecondCnt;

        public long getAccumulatedFirstCnt() {
            return accumulatedFirstCnt;
        }

        public long getAccumulatedSecondCnt() {
            return accumulatedSecondCnt;
        }

        public String getBaseDate() {
            return baseDate;
        }

        public long getFirstCnt() {
            return firstCnt;
        }

        public long getSecondCnt() {
            return secondCnt;
        }

        public String getSido() {
            return sido;
        }

        public long getTotalFirstCnt() {
            return totalFirstCnt;
        }

        public long getTotalSecondCnt() {
            return totalSecondCnt;
        }
    }
}
