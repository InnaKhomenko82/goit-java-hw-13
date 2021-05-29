import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
public class Main {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static void main(String[] args) {
        RetrofitClient client = RetrofitConfig.createClient(BASE_URL,
                GsonConverterFactory.create(), RetrofitClient.class);

        System.out.println("~ ~ ~ ~ ~ ~ ~");
        System.out.println("Считываем данные в список объектов:");
        List <User> listUsers = RetrofitConfig.execute(client.getUser());
        //System.out.println(listUsers);
        for (User user : listUsers) System.out.println(user.getId() + " "+ user.getName());

        System.out.println("~ ~ ~ ~ ~ ~ ~");
        System.out.println("Добавляем новый объект:\n" + newUser());
        listUsers.add(newUser());
        System.out.println("++++");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String dest = gson.toJson(listUsers);
        //System.out.println(dest);
        System.out.println(RetrofitConfig.execute(client.addObject(newUser())));

        System.out.println("~ ~ ~ ~ ~ ~ ~");
        //System.out.println(RetrofitConfig.execute(client.updateUser(newUser(), 2)));

        System.out.println("~ ~ ~ ~ ~ ~ ~");
        //System.out.println(RetrofitConfig.execute(client.deleteUser(8)));
    }


    private static User newUser() {
        return new User.UserBuilder()
                .id(11)
                .name("Inna Khomenko")
                .username("itm.ikhomenko")
                .email("itm.ikhomenko@gmail.com")
                .address(new Address.AddressBuilder()
                    .street("23 veresnya")
                    .suite("13-4")
                    .city("Poltava")
                    .zipcode("36023")
                    .geo(new Geo.GeoBuilder()
                        .latitude(49.56)
                        .longitude(34.50)
                        .build())
                    .build())
                .phone("+380661796258")
                .website("https://github.com/InnaKhomenko82")
                .company(new Company.CompanyBuilder()
                    .nameCompany("GoIT")
                    .catchPhrase("Client Happiness Manager")
                    .bs("Java")
                    .build())
                .build();
    }
}
