package com.example.instest.DataService;

import java.io.FileOutputStream;

public interface FileWrite {
    FileOutputStream openFileOutput (String name, int mode);
}
