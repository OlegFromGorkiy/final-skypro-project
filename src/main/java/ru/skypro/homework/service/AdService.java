package ru.skypro.homework.service;

import ru.skypro.homework.dto.UpdateAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.List;

public interface AdService {
    /**
     * получить все объявления с базы
     *
     * @return все объявления из базы в виде списка
     */
    List<Ad> getAll();

    /**
     * Сохранеие объявления в базу
     *
     * @param ad объявление которое требуется сохранить; не должно быть null
     * @return сохраненный в базу объект; никогда не будет null
     */
    Ad saveAd(Ad ad);

    /**
     * Создание нового объявления и сохранение его в базу.
     *
     * @param newAd     DTO c данными объявления
     * @param author    пользователь, автор объявления
     * @param imagePath путь к изображению объявления
     * @return сохраненный в базу объект
     */
    Ad createAd(UpdateAd newAd, User author, String imagePath);

    /**
     * Получение объявления из базы по Id
     *
     * @param id Id объявления
     * @return null если объявление не найдено
     */
    Ad getById(int id);

    /**
     * Удаление объявления из базы
     *
     * @param ad объявление которое следует удалить; не должно быть null
     */
    void deleteAd(Ad ad);

    /**
     * Обновление данных в объявлении и сохранение изменений в базе
     *
     * @param ad     объявление где будут обновлены данные
     * @param update данные для обновления
     * @return обновленной объявление; никогда не будет null
     */
    Ad updateAd(Ad ad, UpdateAd update);

    /**
     * Список всех объявлений одного автора
     *
     * @param author автор объявлений
     * @return список всех объявлений автора
     */
    List<Ad> getAllByAuthor(User author);
}
