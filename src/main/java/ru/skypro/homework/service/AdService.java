package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.dto.UpdateAd;
import ru.skypro.homework.entity.Ad;


public interface AdService {
    /**
     * получить все объявления с базы
     *
     * @return все объявления из базы в виде объекта DTO
     */
    Ads getAll();

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
     * @param newAd          DTO c данными объявления
     * @param authentication данные авторизированного пользователя, автора объявления
     * @param image          изображение как строка (необходимо извлечь байты)
     * @return сохраненный в базу объект в виде объекта DTO
     */
    AdDTO createAd(UpdateAd newAd, Authentication authentication, String image);

    /**
     * Вся информация из объявления
     *
     * @param id идентификатор объявления
     * @return Вся информация из объявления в виде объекта DTO
     */
    ExtendedAd getFullInfo(int id);

    /**
     * Получение объявления из базы по Id
     *
     * @param id идентификатор объявления
     * @return null если объявление не найдено
     */
    Ad getById(int id);

    /**
     * Удаление объявления
     *
     * @param id             идентификатор объявления
     * @param authentication данные авторизированного пользователя
     */
    void deleteAd(int id, Authentication authentication);

    /**
     * Обновление данных в объявлении и сохранение изменений в базе
     *
     * @param id             объявление где будут обновлены данные
     * @param update         данные для обновления в виде объекта DTO
     * @param authentication данные авторизированного пользователя, должен быть автор объявления или администратор
     * @return обновленное объявление в виде объекта DTO
     */
    AdDTO updateAd(int id, UpdateAd update, Authentication authentication);

    /**
     * Список всех объявлений одного автора
     *
     * @param authentication данные авторизированного пользователя
     * @return список всех объявлений от авторизированного пользователя в виде объекта DTO
     */
    Ads getAllByAuthor(Authentication authentication);
}
