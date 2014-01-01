package kr.kkiro.projects.bukkit.planecart.plane;

import java.util.Iterator;

public class PlaneDataGroup implements Iterable<PlaneDataGroup.PlaneDataEntry> {

  public PlaneData[][] datas;
  
  public PlaneDataGroup(PlaneData[][] datas) {
    this.datas = datas;
  }
  
  public int getWidth() {
    return datas[0].length;
  }
  
  public int getHeight() {
    return datas.length;
  }
  
  public PlaneDataEntry get(int x, int y) {
    if(x < 0 || y < 0 || x >= datas[0].length || y >= datas.length) {
      return new PlaneDataEntry(x, y, null);
    }
    return new PlaneDataEntry(x, y, datas[y][x]);
  }
  
  public PlaneDataEntry getNorth(PlaneDataEntry data) {
    return get(data.x, data.y - 1);
  }
  
  public PlaneDataEntry getWest(PlaneDataEntry data) {
    return get(data.x - 1, data.y);
  }
  
  public PlaneDataEntry getEast(PlaneDataEntry data) {
    return get(data.x + 1, data.y);
  }
  
  public PlaneDataEntry getSouth(PlaneDataEntry data) {
    return get(data.x, data.y + 1);
  }
  
  public PlaneDataEntry getNorthUntil(PlaneDataEntry data) {
    PlaneDataEntry data2 = getNorth(data);
    if(data2.data != null && data.data != null && data2.data.type == data.data.type) return getNorthUntil(data2);
    return data2;
  }
  
  public PlaneDataEntry getSouthUntil(PlaneDataEntry data) {
    PlaneDataEntry data2 = getSouth(data);
    if(data2.data != null && data.data != null && data2.data.type == data.data.type) return getSouthUntil(data2);
    return data2;
  }
  
  public PlaneDataEntry getEastUntil(PlaneDataEntry data) {
    PlaneDataEntry data2 = getEast(data);
    if(data2.data != null && data.data != null && data2.data.type == data.data.type) return getEastUntil(data2);
    return data2;
  }
  
  public PlaneDataEntry getWestUntil(PlaneDataEntry data) {
    PlaneDataEntry data2 = getWest(data);
    if(data2.data != null && data.data != null && data2.data.type == data.data.type) return getWestUntil(data2);
    return data2;
  }
  
  public boolean isNeighbor(PlaneDataEntry data, PlaneDataEntry data2) {
    return (Math.abs(data.x - data2.x) + Math.abs(data.y - data2.y)) == 1;
  }
  
  public byte findNearAir(PlaneDataEntry data) {
    byte i = 0;
    if(getNorthUntil(data).data != null) i += 1 + 8;
    if(getSouthUntil(data).data != null) i += 1 + 16;
    if(getEastUntil(data).data != null) i += 1 + 32;
    if(getWestUntil(data).data != null) i += 1 + 64;
    return i;
  }
  
  public static class PlaneDataEntry {
    public int x;
    public int y;
    public PlaneData data;
    
    public PlaneDataEntry(int x, int y, PlaneData data) {
      this.x = x;
      this.y = y;
      this.data = data;
    }
  }
  
  public static class PlaneDataEntryMarked extends PlaneDataEntry {

    public boolean marked = false;
    
    public PlaneDataEntryMarked(int x, int y, PlaneData data) {
      super(x, y, data);
      // TODO Auto-generated constructor stub
    }
    
  }

  @Override
  public Iterator<PlaneDataEntry> iterator() {
    return new Iterator<PlaneDataGroup.PlaneDataEntry>() {
      
      private int currentPosition = 0;
      
      @Override
      public void remove() {
        throw new UnsupportedOperationException();
        
      }
      
      @Override
      public PlaneDataEntry next() {
        PlaneDataEntry entry = get(currentPosition % getWidth(), currentPosition / getWidth());
        currentPosition += 1;
        return entry;
      }
      
      @Override
      public boolean hasNext() {
        return currentPosition < getWidth() * getHeight();
      }
    };
  }

}
