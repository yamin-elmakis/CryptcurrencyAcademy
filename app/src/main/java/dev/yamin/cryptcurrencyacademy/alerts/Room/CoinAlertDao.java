package dev.yamin.cryptcurrencyacademy.alerts.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by yuval on 16/03/2018.
 */
@Dao
public interface CoinAlertDao {
    @Query("SELECT * FROM CoinAlertItem")
    List<CoinAlertItem> getAll();

    @Query("SELECT * FROM CoinAlertItem WHERE id IN (:coinAlertIds)")
    List<CoinAlertItem> loadAllByIds(int[] coinAlertIds);

    @Query("SELECT * FROM CoinAlertItem WHERE symbol LIKE :symbol LIMIT 1")
    CoinAlertItem findBySymbol(String symbol);

    @Insert
    void insertAll(CoinAlertItem... coinAlertItems);

    @Delete
    void delete(CoinAlertItem coinAlertItem);
}
