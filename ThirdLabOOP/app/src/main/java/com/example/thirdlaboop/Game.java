package com.example.thirdlaboop;

import java.io.Serializable;
import java.sql.Date;

public class Game implements Serializable {
    String GameName;
    String Developer;
    String Publisher;
    String Genre;
    int GenrePos;
    Date Release;
    String Platform;
    int PlatformPos;
    String PEGI;
    int PEGIPos;
    String Version;
    public Game(){

    }
}
