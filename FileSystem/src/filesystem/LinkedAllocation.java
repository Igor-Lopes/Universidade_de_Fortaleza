package filesystem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Igor
 */
public class LinkedAllocation implements Serializable {

    public int capacity; // Capacidade de armazenamento
    public int numberofblocks; // Total de Blocos da mídia.
    public int blocksize; // Tamanho do bloco.
    public int mid = 0; // ID do bloco.
    public ArrayList<MemoryCell> blocks = new ArrayList<>(); //Lista de Blocos.
    public SuperBlock s;

    public void installFile(int c, int b) throws IOException {
       System.out.println("Instalando Mídia de Armazenamento");
        this.capacity = c * 1024; // Transforma MB para KB
        blocksize = b; // Tamanho do bloco em KB
        this.numberofblocks = capacity / b; // Calcula o número de blocos que o sistema vai possuir.
        this.s = new SuperBlock (c,b) {};
        s.numberofblocks = numberofblocks;
        s.freeblocks = new boolean[numberofblocks]; //Seta tamanho do array de status dos blocos.
        this.blocks.add(s);

        s.freeblocks[0] = true;
        for (int i = 1; i < s.freeblocks.length; i++) {
            s.freeblocks[i] = false;
        }

        for (int i = 1; i < numberofblocks; i++) {
            MemoryCell m;
            m = new MemoryCell() {
            };
            this.blocks.add(m);
            this.addFreeBlockIndex(i);
            this.mid++;
        }
        System.out.println(" Blocos: " + numberofblocks + " Blocos livres:" + getFreeBlocks());
       // System.out.println(s.linkedallocation.size());
        this.rewriteFile();
    }

    public void rewriteFile() throws IOException {

        System.out.println("---------------------------- Rewriting Storage ----------------------------");
        ObjectOutputStream saida = null; //ok
        String nomeArquivo = "C:\\Users\\Igor\\Desktop\\Storage.bin"; //ok
        saida = new ObjectOutputStream(new FileOutputStream(nomeArquivo, false));//ok - false
        File f = new File(nomeArquivo);
        f.delete();
        MemoryCell m;
        for (int i = 0; i < this.numberofblocks; i++) {
            m = this.blocks.get(i);
            saida.writeObject(m);

        }
        saida.reset();
        this.blocks.clear();
    }

    public void readFile() throws Exception {
        System.out.println("---------------------------- Reading Storage ----------------------------");
        ObjectInputStream entrada = null;
        String nomeArquivo = "C:\\Users\\Igor\\Desktop\\Storage.bin";
        entrada = new ObjectInputStream(new FileInputStream(nomeArquivo));
        SuperBlock superblock = (SuperBlock) entrada.readObject();
        this.blocks.add(superblock);
        this.s = (SuperBlock) this.blocks.get(0);
        this.numberofblocks = s.totalofblocks;
        this.blocksize = s.block_size;
        this.numberofblocks = s.totalofblocks;
        for (int i = 1; i < this.numberofblocks; i++) {
            MemoryCell m = (MemoryCell) entrada.readObject();
            this.blocks.add(m);
        }
        entrada.close();

        System.out.println(blocks.size());
       
    }

    public int getFreeBlocks() {
        int count = 0;
        for (int i = 0; i < s.freeblocks.length; i++) {
            if (s.freeblocks[i] == false) {
                count++;
            }
        }
        return count;
    }

    public void storeFile(String path) throws FileNotFoundException, IOException {
        try {
            readFile();
        } catch (Exception ex) {
            Logger.getLogger(ContigousAllocation.class.getName()).log(Level.SEVERE, null, ex);
        }
        //------------------------------------------------------------------------ OK
        int number_blocks;
        int freeblocks;
        byte[] bFile;
        File file;
        String filename;
        //------------------------------------------------------------------------ OK

        file = new File(path);
        filename = file.getName();
        bFile = new byte[(int) file.length()];
        freeblocks = this.getFreeBlocks();
        number_blocks = this.getNumberOfBlocks((int) file.length());
        //------------------------------------------------------------------------ OK
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        dis.readFully(bFile);
        dis.close();
        //------------------------------------------------------------------------ OK

        if (number_blocks + 1 > freeblocks) {
            System.out.println("-------------- Aborted - File is too large - Xx Out Of Memory xX --------------");
//------------------------------------------------------------------------------------------------------------------- OK
        } else {

            int header_index = s.linkedallocation.get(0);
            this.removeFreeBlockIndex();
            s.FAT.put(filename, header_index);
            Header h = new Header(header_index, s.linkedallocation.get(0), filename, number_blocks, (int) file.length()) {
            };
            this.blocks.set(header_index, h);
            s.freeblocks[header_index] = true;
            int lastindex = 0;
//--------------------------------------------------------------------------------------------------------------------------------
            for (int i = 1; i <= number_blocks; i++) {
                if (i == number_blocks) {
                    DataBlock d = new DataBlock(blocksize) {
                    };
                    int actualblock = s.linkedallocation.get(0);
                    MemoryCell m = this.blocks.get(actualblock); // VERIFICA SE O BLOCO REALMENTE ESTÁ GENERICO E DESALOCADO.
                    this.blocks.set(actualblock, d);
                    s.freeblocks[actualblock] = true;
                    this.removeFreeBlockIndex();
                    // int nextindex = s.linkedallocation.get(0);
                    d.next = -1;
                    for (int j = 0; j < d.array.length; j++) {
                        if (lastindex < bFile.length) {
                            d.array[j] = bFile[lastindex];
                        }
                        lastindex++;
                    }
//----------------------------------------------------------------------------------------------------------------------------------
                } else {
                    DataBlock d = new DataBlock(blocksize) {
                    };

                    int actualblock = s.linkedallocation.get(0);
                    MemoryCell m = this.blocks.get(actualblock); // VERIFICA SE O BLOCO REALMENTE ESTÁ GENERICO E DESALOCADO.
                    this.blocks.set(actualblock, d);
                    s.freeblocks[actualblock] = true;
                    this.removeFreeBlockIndex();
                    int nextindex = s.linkedallocation.get(0);
                    d.next = nextindex;
                    for (int j = 0; j < d.array.length; j++) {
                        if (lastindex < bFile.length) {
                            d.array[j] = bFile[lastindex];
                        }
                        lastindex++;
                    }
                }

            }

        }
        //------------------------------------------------------------------------
        System.out.println("File saved successfully !");
        System.out.println(" Blocos: " + numberofblocks + " Lista:" + blocks.size() + " Blocos livres:" + getFreeBlocks());
        rewriteFile();
    }

    public void addFreeBlockIndex(int i) {
        s.linkedallocation.add(i);
        Collections.sort(s.linkedallocation);
    }

    public void removeFreeBlockIndex() {
        s.linkedallocation.remove(s.linkedallocation.get(0));
        Collections.sort(s.linkedallocation);

    }

    public void removeFile(String filename) throws IOException {
        try {
            readFile();
        } catch (Exception ex) {
            Logger.getLogger(ContigousAllocation.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("--------------------------------------- Deleting File ---------------------------------------");

        //------------------------------------------------------------------------ //ok
        File f;
        int header;
        int actualblock;
        int number_blocks;
        Header h;
        MemoryCell clear = new MemoryCell() {
        };

        //------------------------------------------------------------------------ //ok
        header = s.FAT.get(filename);
        this.addFreeBlockIndex(header);
        h = (Header) this.blocks.get(header);
        s.freeblocks[header] = false;
        actualblock = h.firstblock;
        number_blocks = h.totalofblocks;
        f = new File(filename);
        this.blocks.set(header, clear);
        s.FAT.remove(filename);
        //------------------------------------------------------------------------ //ok
        for (int i = 1; i <= number_blocks; i++) {
            DataBlock d = (DataBlock) this.blocks.get(actualblock); // VERIFICA SE O BLOCO REALMENTE ESTÁ GENERICO E ALOCADO.
            this.blocks.set(actualblock, clear);
            s.freeblocks[actualblock] = false;
            this.addFreeBlockIndex(actualblock);
            actualblock = d.next;
        }
        //------------------------------------------------------------------------ 
        this.rewriteFile();
        //------------------------------------------------------------------------
        System.out.println("File was removed successfully !");
        System.out.println(" Blocos: " + numberofblocks + " Lista:" + blocks.size() + " Blocos livres:" + getFreeBlocks());
    }

    public void restoreFile(String filename) throws FileNotFoundException, IOException {
        try {
            readFile();
        } catch (Exception ex) {
            Logger.getLogger(ContigousAllocation.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("--------------------------------------- Recovering File ---------------------------------------");

        //------------------------------------------------------------------------
        File f;
        byte[] bFile;
        int header;
        int actualblock;
        int lastindex;
        int number_blocks;
        int lenght;
        Header h;
        //------------------------------------------------------------------------
        header = s.FAT.get(filename);
        // MemoryCell m = (MemoryCell) this.blocks.get(header);
       // System.out.println(s.FAT.entrySet());
        h = (Header) this.blocks.get(header);

        actualblock = h.firstblock;
        number_blocks = h.totalofblocks;
        f = new File(filename);
        lastindex = 0;
        lenght = h.byteslenght;
        bFile = new byte[lenght];
        //------------------------------------------------------------------------
        for (int i = 1; i <= number_blocks; i++) {
            DataBlock d = (DataBlock) this.blocks.get(actualblock);
            for (int j = 0; j < d.array.length; j++) {
                if (lastindex < bFile.length) {
                    bFile[lastindex] = d.array[j];
                }
                lastindex++;
                if (lastindex >= bFile.length) {
                    break;
                }
            }
            actualblock = d.next;
        }

        //------------------------------------------------------------------------
        File outFile = new File("C:\\Users\\Igor\\Desktop\\" + "Recovery" + filename);
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(outFile));
        dos.write(bFile, 0, bFile.length);
        dos.close();
        System.out.println("File was recovered successfully !");
        System.out.println(" Blocos: " + numberofblocks + " Lista:" + blocks.size() + " Blocos livres:" + getFreeBlocks());
        //this.rewriteFile();

    }

    public int getNumberOfBlocks(int f_size) {
        int file_size = f_size;
        int count = 0;
        while (file_size != 0) {
            if (file_size - blocksize * 1024 > 0) {
                file_size = file_size - blocksize * 1024;
                count++;
            }
            if (file_size - blocksize * 1024 < 0) {
                file_size = 0;
                count++;
            }
            if (file_size - blocksize * 1024 == 0) {
                file_size = 0;
                count++;
            } else {

            }
        }
        System.out.println("Blocos Necessários: " + count);
        return count;
    }

    public static void main(String[] args) throws IOException {
        String op;
        Scanner s = new Scanner(System.in);
        LinkedAllocation la = new LinkedAllocation();
    /* la.installFile(2048, 4);
       while (true) {
           System.out.println(" Store / Remove/ Restore ");
           op = s.nextLine();
            if (op.equalsIgnoreCase("Store")) {
                System.out.print("File Path: ");
                String path = s.nextLine();
                la.storeFile(path);
            }
            if (op.equalsIgnoreCase("Restore")) {
                System.out.print("File Name: ");
                String path = s.nextLine();
                la.restoreFile(path);
            }
            if (op.equalsIgnoreCase("Remove")) {
                System.out.print("File Name: ");
                String path = s.nextLine();
               la.removeFile(path);
            }

       }  */

    } 
             
}
