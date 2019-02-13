package config;

import android.content.Context;

import com.yaheen.geturlchange.data.DataRepository;
import com.yaheen.geturlchange.data.LocalDataSource;
import com.yaheen.geturlchange.data.RemoteDataSource;

/**
 * Created by Administrator on 2017/9/25.
 */

public class Injection {
    public static DataRepository provideTasksRepository(Context context) {
        return DataRepository.getInstance(RemoteDataSource.getInstance(), LocalDataSource.getInstance(context));
    }
}
