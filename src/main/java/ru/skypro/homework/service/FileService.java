package ru.skypro.homework.service;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Сервис для чтения и записи файлов
 */
public interface FileService {
    /**
     * Запись файла в файловую систему
     *
     * @param path   путь к файлу
     * @param source данные для записи
     * @throws IOException ошибки ввода-вывода
     */
    void saveFile(Path path, byte[] source) throws IOException;

    /**
     * Чтение из файла из в файловой системы
     *
     * @param path путь к файлу
     * @return данные файла
     * @throws IOException ошибки ввода-вывода
     */
    byte[] readFile(Path path) throws IOException;
}
