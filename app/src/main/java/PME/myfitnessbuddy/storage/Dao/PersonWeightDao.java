package PME.myfitnessbuddy.storage.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import PME.myfitnessbuddy.model.PersonWeight;

@Dao
public interface PersonWeightDao {


    @Insert
    List<Long> insert(PersonWeight... personWeights);

    @Update
    void update(PersonWeight... personWeights);

    @Delete
    void delete(PersonWeight... personWeights);

    @Query("DELETE FROM PersonWeight")
    void deleteAll();

    @Query("SELECT count(*) FROM PersonWeight")
    int count();

    @Query("SELECT * from PersonWeight ORDER BY id")
    List<PersonWeight> getPersonWeights();

    @Query("SELECT * from PersonWeight ORDER BY id DESC LIMIT 10")
    List<PersonWeight> getLastPersonWeights();

    @Query("SELECT * from PersonWeight ORDER BY id DESC LIMIT 1")
    PersonWeight getLastEntry();

}
