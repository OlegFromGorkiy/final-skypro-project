package ru.skypro.homework.service;

import java.io.IOException;
import java.nio.file.Path;

public interface FileService {
    /**
     * Запись файла
     *
     * @param path   путь к файлу
     * @param source данные для записи
     * @throws IOException ошибки ввода-вывода
     */
    void saveFile(Path path, byte[] source) throws IOException;

    /**
     * Чтение из файла
     *
     * @param path путь к файлу
     * @return данные файла
     * @throws IOException ошибки ввода-вывода
     */
    byte[] readFile(Path path) throws IOException;
}
