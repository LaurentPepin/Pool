package com.example.laure.pool;

/**
 * Created by laure on 2017-01-14.
 */
public class ModifyString {

    static int maxLength = 14;

    /*
    Transform the given String to this format : Format
     */
    public static String transformToNormal (String string){
        string = string.toLowerCase();
        int index = string.indexOf('-');
        if(index>0){
            string = string.substring(0,1).toUpperCase() + string.substring(1,index+1) + string.substring(index+1,index+2).toUpperCase() + string.substring(index+2);
        }
        else {
            string = string.substring(0, 1).toUpperCase() + string.substring(1);
        }
        return string;
    }

    /*
    Split a String in an array of Strings. Split when " " is read.
     */
    public static String[] getStringArray (String string){
        return string.split(" ");
    }

    /*
    Return true if String is used in the array of Strings passed in parameter
     */
    public static boolean nameAlredayUsed (String[] strings, String string){
        for(int i=0; i<strings.length; i++){
            if(strings[i].equals(string)){
                return true;
            }
        }
        return false;
    }



    /*
    Creates a String using initials: L. Pepin
     */
    public static String createFirstDotLastName (String firstName, String lastName){
        int index = firstName.indexOf('-');
        if(index>0){
            return firstName.substring(0,1) + "-" + firstName.substring(index+1,index+2).toUpperCase() + ". " + lastName;
        }
        return firstName.substring(0,1) + ". " + lastName;

    }

    public static String createInitialName (String firstName, String lastName) {
        int index = firstName.indexOf('-');
        if(index>0){
            return firstName.substring(0,1) + "-" + firstName.substring(index+1,index+2).toUpperCase() + ". " + lastName.substring(0,1) + ".";
        }
        return firstName.substring(0,1) + ". " + lastName.substring(0,1) + ".";

    }

    public static String shrinkComposedName (String string){
        int index = string.indexOf('-');
        if(index>0){
            return string.substring(0,index+1) + string.substring(index+1,index+2).toUpperCase() + ".";
        }
        return string;
    }

    /*
    Method to set a pooler name
     */
    public static String setPoolerName (int index, String[] firstNames, String[] lastNames, String[] poolersNames){
        String poolerName = firstNames[index];
        if(poolerName.length() > maxLength || nameAlredayUsed(poolersNames, poolerName)){
            poolerName = shrinkComposedName(firstNames[index]);
            if(poolerName.length() > maxLength || nameAlredayUsed(poolersNames, poolerName)){
                poolerName = createFirstDotLastName(firstNames[index], lastNames[index]);
                if(poolerName.length() > maxLength){
                    poolerName = createInitialName(firstNames[index], lastNames[index]);
                }
            }
        }
        return poolerName;
    }
}
