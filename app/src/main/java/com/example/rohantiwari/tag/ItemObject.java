

package com.example.rohantiwari.tag;

public class ItemObject {

    public String name;
    public String addr;

    public ItemObject( String addr,String name) {
        this.name = name;
        this.addr=addr;
    }

    public String getName() {

        return name;
    }
    public String getAddr(){
        return addr;
    }

    public void setName(String name) {

        this.name = name;
    }
    public void setAddr(String addr){
        this.addr=addr;
    }
}
