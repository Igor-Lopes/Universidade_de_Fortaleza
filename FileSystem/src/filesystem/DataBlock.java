package filesystem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;

/**
 *
 * @author Igor
 */
public abstract class DataBlock extends MemoryCell implements Serializable{
    int blocksizebytes;
    int sizeusedbytes;
    public int next;
    public byte [] array;
    private static final long serialVersionUID = 1000L;

    public DataBlock(int blocksize){
        this.next = 0;
        this.blocksizebytes = blocksize * 1024;
        //this.sizeusedbytes = sizeused;
        this.array = new byte[blocksize * 1024];
    }
}
