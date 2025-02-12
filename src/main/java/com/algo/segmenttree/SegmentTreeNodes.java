package com.algo.segmenttree;

class SegmentTreeNodes {
    int value;
    double x, y;
    int start, end;

    SegmentTreeNodes() {
        value = -1;
        x = -1;
        y = -1;
        start = -1;
        end = -1;
    }

    SegmentTreeNodes(int value, double x, double y, int start, int end) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.start = start;
        this.end = end;
    }
}