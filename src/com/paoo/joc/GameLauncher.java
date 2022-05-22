
package com.paoo.joc;

import com.paoo.joc.util.Database;

public class GameLauncher {

    public GameLauncher (){
        Window.getInstance();
    }

    public static void main (String[] args) {
        new GameLauncher();
        Database database = new Database();
        database.connect();

    }
}
