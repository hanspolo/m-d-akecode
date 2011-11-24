/*
 *  m(d)akecode is a code generator for model driven development.
 *  Copyright (C)    2011 Philipp "Hanspolo" Hirsch
 *                  2011 Dennis Priefer
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package makecode.ui;

import java.io.File;

public class Arguments {

    private static Boolean help = false;
    private static File file;
    private static String type;
    private static String transform;
    private static Boolean createDb = false;
    
    public static void check(String[] args) {
        
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--help")) {
                help = true;
            }
            
            if (args[i].equals("-f")) {
                file = new File(args[i+1]);
            }
            
            if (args[i].equals("-i")) {
                type = args[i+1];
            }
            
            if (args[i].equals("-db")) {
                createDb = true;
            }
            
            if (args[i].equals("-t")) {
                transform = args[i+1];
            }
        }
    }
    
    public static Boolean getHelp() {
        return help;
    }
    
    public static File getFile() {
        return file;
    }
    
    public static String getFileType() {
        return type;
    }
    
    public static String getTransform() {
        return transform;
    }
    
    public static Boolean getCreateDb() {
        return createDb;
    }
}
