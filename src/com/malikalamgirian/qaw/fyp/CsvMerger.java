/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

/**
 *
 * @author wasif
 */
public class CsvMerger {

    public CsvMerger(String files_To_Merge_Path_Name_URL[], String output_Folder_Path_Name_URL,
            String new_Csv_Filename) throws Exception {
        /*
         * SET output_Folder_Path_Name_URL
         */
        this.output_Folder_Path_Name_URL = output_Folder_Path_Name_URL;

        /*
         * SET files_To_Merge_Path_Name_URL
         */
        this.files_To_Merge_Path_Name_URL = files_To_Merge_Path_Name_URL;

        /*
         * SET new_Csv_Filename
         */
        this.new_Csv_Filename = new_Csv_Filename;

        /*
         * SET merged_File_Path_Name
         */
        this.setMerged_File_Path_Name(new_Csv_Filename, files_To_Merge_Path_Name_URL);

        /*
         * Call Process
         */
        process();
    }

    /*
     * Declarations
     */
    private String output_Folder_Path_Name_URL = null,
            files_To_Merge_Path_Name_URL[] = null,
            new_Csv_Filename = null,
            merged_File_Path_Name = null;

    /*
     * Helper Methods
     */
    private void process() throws Exception {
        /*
         * Local declarations
         */
        BufferedReader bReader[] = null;
        BufferedWriter bWriter = null;

        String line_Read = null;

        try {
            /*
             * Set bReader[] 
             */
            bReader = new BufferedReader[this.files_To_Merge_Path_Name_URL.length];

            for (int i = 0; i < bReader.length; i++) {
                /*
                 * Set Readers
                 */
                bReader[i] = FileSystemManager.readFile(files_To_Merge_Path_Name_URL[i]);
            }

            /*
             * Get bWriter
             */
            bWriter = FileSystemManager.createFile(merged_File_Path_Name);



            /*
             * Perform Writing
             */
            for (int i = 0; i < bReader.length; i++) {
                /*
                 * Validate and get attribute header
                 */
                if (i == 0) {
                    bWriter.write(validateAndGetAttributes(bReader));

                    /*
                     * NewLine
                     */
                    bWriter.newLine();
                }

                /*
                 * Write all the instances
                 */
                while ((line_Read = bReader[i].readLine()) != null) {
                    /*
                     * Write Instance
                     */
                    bWriter.write(line_Read);

                    /*
                     * Insert NewLine
                     */
                    bWriter.newLine();
                }
            }

             /*
             * Flush Writer
             */
            bWriter.flush();

            /*
             * Close Readers and Writer
             */            
            bWriter.close();

            for (int i = 0; i < bReader.length; i++) {
                    bReader[i].close();
            }


        } catch (Exception e) {
            throw new Exception("CsvMerger : process() ");
        }
    }


    /*
     * Accessors
     */
    private String getMerged_File_Path_Name() {
        return merged_File_Path_Name;
    }

    private void setMerged_File_Path_Name(String new_Csv_Filename, String files_To_Merge_Path_Name_URL[]) throws Exception {
        /*
         * Local Declarations
         */
        String extension = ".csv";

        try {
            if (new_Csv_Filename.equalsIgnoreCase("") || new_Csv_Filename == null) {
                /*
                 * If new_Csv_Filename not given
                 *
                 * SET "files_To_Merge_Path_Name_URL[0]" as name
                 */
                merged_File_Path_Name = output_Folder_Path_Name_URL + File.separatorChar + FileSystemManager.getFileNameWithoutExtension(files_To_Merge_Path_Name_URL[0]) + extension;

            } else {
                /*
                 * If new_Cvs_Filename given
                 */
                merged_File_Path_Name = output_Folder_Path_Name_URL + File.separatorChar + new_Csv_Filename + extension;

            }

        } catch (Exception e) {
            throw new Exception("CsvMerger : setMerged_File_Path_Name() ");
        }
    }

    private String validateAndGetAttributes(BufferedReader[] bReader) throws Exception {
        /*
         * Local Declarations
         */
        String attributes_To_Return = null;
        String attribute_Header[] = null;

        try {
            /*
             * Set attribute_Header[]
             */
            attribute_Header = new String[bReader.length];

            for (int i = 0; i < bReader.length; i++) {
                attribute_Header[i] = bReader[i].readLine();
            }

            /*
             * Validate all
             */
            for (int j = attribute_Header.length -1 ; j > 0; --j) {
                /*
                 * Perform check
                 */
                if (!attribute_Header[j].equalsIgnoreCase(attribute_Header[j - 1])) {
                    throw new Exception(attribute_Header[j] + " : : " + attribute_Header[j - 1] + ", are not equal.");
                }
            }

            /*
             * Set attributes_To_Return, [0]
             */
            attributes_To_Return = attribute_Header[0];

        } catch (Exception e) {
            throw new Exception("CsvMerger : validateAndGetAttributes() ");
        }
        return attributes_To_Return;
    }
}
