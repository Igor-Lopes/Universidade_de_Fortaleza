package filesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public abstract class SuperBlock extends MemoryCell implements Serializable  {

    public HashMap<String, Integer> FAT = new HashMap<>();
    public int totalofblocks;
    public int lenghtarrayofbytes;
    public int block_size;
    public int numberofblocks;
    private static final long serialVersionUID = 1000L;
    public boolean [] freeblocks;
    public ArrayList<Integer> linkedallocation = new ArrayList<>();
    public SuperBlock(int tb, int blocksize) {
        this.totalofblocks = tb;
        this.freeblocks = new boolean [totalofblocks];
        this.block_size = blocksize;
    }
}
