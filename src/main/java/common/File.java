package common;

import utils.Tools;

public abstract class File implements FileConverting {
    protected java.io.File file;

    public File(java.io.File file) {
        this.file = file;
    }

    public String getTitleFromFilename() {
        String filename = file.getName();
        filename = Tools.getNameWithoutExtension(filename).replaceFirst("—", "-");
        int dashPosition = filename.indexOf("-");
        if (dashPosition < 0)
            return filename;//all name is title

        return filename.substring(++dashPosition).trim();
    }
}
