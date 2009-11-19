package org.petah.spring.bai.map.metal;

/**
 *
 * @author Petah
 */
public class MetalZone {

    private int x;
    private int y;
    private int width;
    private int height;
    private float averageMetal;
    private long totalMetal;

    public MetalZone(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
//        processMetalZone();
    }

//    private void processMetalZone(CachedMetalMap cachedMetalMap) {
//        totalMetal = 0;
//        for (int y = this.y; y < this.y + height; y += 1) {
//            for (int x = this.x; x < this.x + width; x += 1) {
//                if (x >= cachedMetalMap.getWidth() || y >= cachedMetalMap.getHeight()) {
//                    continue;
//                }
//                totalMetal += cachedMetalMap.getValue(x, y);
//            }
//        }
//        averageMetal = totalMetal / (width * height);
//    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getAverageMetal() {
        return averageMetal;
    }

    public long getTotalMetal() {
        return totalMetal;
    }
}
