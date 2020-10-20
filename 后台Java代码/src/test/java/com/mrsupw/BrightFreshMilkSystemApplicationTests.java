package com.mrsupw;

import com.mrsupw.entity.*;
import com.mrsupw.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@SpringBootTest

class BrightFreshMilkSystemApplicationTests {


    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private MilkDrinkMapper milkDrinkMapper;

    @Autowired
    private YogurtMapper yogurtMapper;

    @Autowired
    private JellyMapper jellyMapper;

    @Autowired
    private PureMilkMapper pureMilkMapper;

    @Autowired
    private MilkOrderMapper milkOrderMapper;

    @Autowired
    private SaleItemMapper saleItemMapper;


    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    void testAddAdmin(){
        Admin admin = new Admin();
        admin.setUsername("MrSupW3");
        admin.setPassword("123456");
        adminMapper.insert(admin);
        System.out.println("insert successfully");
    }


    @Test
    void addFakeJellyData(){
        for(int i=10;i<110;i++){
            String code = "A" + i;
            String description = "Tasty";
            float price = (float) (2.062 + i);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,2020);
            calendar.set(Calendar.MONTH,Calendar.OCTOBER);
            calendar.set(Calendar.DAY_OF_MONTH,i % 30 + 1);
            Date date = calendar.getTime();
            String[] shelfLives =  new String[]{"90 days","180 days","120 days"} ;
            String[] flavors = new String[]{"strawberry","banana","apple","pear","orange"};
            Jelly jelly = new Jelly();
            jelly.setCode(code);
            jelly.setDescription(description);
            jelly.setPrice(price);
            jelly.setProductionDate(date);
            jelly.setShelfLife(shelfLives[(int)(Math.random()*3)]);
            jelly.setFlavor(flavors[(int)(Math.random()*5)]);
            jelly.setQuantity((int)(Math.random()*10000) + 5000);
            jellyMapper.insert(jelly);
        }
    }

    @Test
    void addFakeMilkDrinkData(){
        for(int i=10;i<110;i++){
            String code = "B" + i;
            String description = "Tasty";
            float price = (float) (2.062 + i);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,2020);
            calendar.set(Calendar.MONTH,Calendar.OCTOBER);
            calendar.set(Calendar.DAY_OF_MONTH,i % 30 + 1);
            Date date = calendar.getTime();
            String[] shelfLives =  new String[]{"90 days","180 days","120 days"} ;
            String[] flavors = new String[]{"strawberry","banana","apple","pear","orange"};
            String sugar = "10g/L";
            MilkDrink milkDrink = new MilkDrink();
            milkDrink.setCode(code);
            milkDrink.setDescription(description);
            milkDrink.setPrice(price);
            milkDrink.setProductionDate(date);
            milkDrink.setShelfLife(shelfLives[(int)(Math.random()*3)]);
            milkDrink.setFlavor(flavors[(int)(Math.random()*5)]);
            milkDrink.setSugar(sugar);
            milkDrink.setQuantity((int)(Math.random()*10000) + 5000);
            milkDrinkMapper.insert(milkDrink);
        }
    }


    @Test
    void addFakePureMilkData(){
        for(int i=10;i<110;i++){
            String code = "C" + i;
            String description = "Tasty";
            float price = (float) (2.062 + i);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,2020);
            calendar.set(Calendar.MONTH,Calendar.OCTOBER);
            calendar.set(Calendar.DAY_OF_MONTH,i % 30 + 1);
            Date date = calendar.getTime();
            String[] shelfLives =  new String[]{"90 days","180 days","120 days"} ;
            String[] countries = new String[]{"China","Spanish","Canada","Fresh","Denmark"};
            String[] butterfats = new String[]{"5g/L","10g/L","20g/L","25g/L","30g/L"};
            String[] proteins = new String[]{"10g/L","15g/L","25g/L","30g/L","50g/L"};
            PureMilk pureMilk = new PureMilk();
            pureMilk.setCode(code);
            pureMilk.setDescription(description);
            pureMilk.setPrice(price);
            pureMilk.setProductionDate(date);
            pureMilk.setShelfLife(shelfLives[(int)(Math.random()*3)]);
            pureMilk.setCountryOfOrigin(countries[(int)(Math.random()*5)]);
            pureMilk.setButterfat(butterfats[(int)(Math.random()*5)]);
            pureMilk.setProtein(proteins[(int)(Math.random()*5)]);
            pureMilk.setQuantity((int)(Math.random()*10000) + 5000);
            pureMilkMapper.insert(pureMilk);
        }
    }

    @Test
    void addFakeYogurtData(){
        for(int i=10;i<110;i++){
            String code = "D" + i;
            String description = "Tasty";
            float price = (float) (2.062 + i);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,2020);
            calendar.set(Calendar.MONTH,Calendar.OCTOBER);
            calendar.set(Calendar.DAY_OF_MONTH,i % 30 + 1);
            Date date = calendar.getTime();
            String[] shelfLives =  new String[]{"27 days","21 days","24 days"} ;
            String[] types = new String[]{"pure","not pure"};
            Yogurt yogurt = new Yogurt();
            yogurt.setCode(code);
            yogurt.setDescription(description);
            yogurt.setPrice(price);
            yogurt.setProductionDate(date);
            yogurt.setShelfLife(shelfLives[(int)(Math.random()*3)]);
            yogurt.setType(types[(int)(Math.random()*2)]);
            yogurt.setDiluteConcentration("medium");
            yogurt.setQuantity((int)(Math.random()*10000) + 5000);
            yogurtMapper.insert(yogurt);
        }
    }



    @Test
    public void testQuery(){
        MilkDrink milkDrink = milkDrinkMapper.selectById(1310647431158743000L);
        System.out.println(milkDrink);
    }

    @Test
    public void addAllFakeData(){
        this.addFakeJellyData();
        this.addFakeMilkDrinkData();
        this.addFakePureMilkData();
        this.addFakeYogurtData();
    }

    @Test
    public void testOrderDatabase(){
        for (int i = 100; i < 130; i++) {
            MilkOrder milkOrder = new MilkOrder();
            milkOrder.setCode("O" + i);
            milkOrder.setIsHistory(0);
            milkOrder.setDescription("To KEKE");
            milkOrderMapper.insert(milkOrder);
        }
    }

    @Test
    public void mapTest(){
        Map<String, String> stringHashMap = new HashMap<String, String>();
    }
}
