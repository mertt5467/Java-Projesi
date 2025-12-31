import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class App {
    static String password = "1234";
    static String[] urunler = new String[40]; // Otomata koyulabilecek maksimum urun sayisi 40'tir.
    static int[][] fiyatStok = new int[40][2];
    static int urunSayisi = 0;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws Exception{
        varsayilanList();
        while (true) {
            System.out.println("\n--- ANA MENU ---");
            System.out.println("1 - Siparis");
            System.out.println("2 - Admin Paneli");
            System.out.println("3 - Programi bitir");
            System.out.print("Islem seciniz: ");
            int giris = input.nextInt();
            input.nextLine();
            if (giris == 1) {
                kullanici();
            } else if (giris == 2) {
                int dogrulama = adminDogrulama();
                if (dogrulama == 1) {
                    adminPanel();
                }
            } else if (giris == 3) {
                urunKaydet();
                System.out.println("Bitiriliyor...");
                Thread.sleep(500);
                System.exit(0);
                break;
            } else {
                System.out.println("Lutfen gecerli bir sayi giriniz.");
                Thread.sleep(1000);
            }
        }
    }

    static void kullanici() throws Exception {
        while (true) {
            double paraUstu;
            int numara = 0;
            System.out.println("Lutfen alacaginiz urunun numarasini yaziniz. Geri donmek icin 0'i tuslayiniz.");
            while (true) {
                yiyecekList();
                numara = input.nextInt();
                if (numara == 0) {
                    return;
                } else if (numara > urunSayisi || numara < 0) {
                    System.out.println("Gecerli bir sayi giriniz!");
                    Thread.sleep(1000);
                    continue;
                } else {
                    if (fiyatStok[numara - 1][1] > 0) {

                        break;
                    } else {
                        System.out.println("Sectiginiz urunun stogu kalmamistir.");
                        Thread.sleep(1000);
                        continue;
                    }

                }
            }
            while (true) {
                int deneme = 0;
                int ucret = fiyatBul(numara);

                if (ucret == -1) {
                    System.out.println("Gecersiz sayi, basa donuluyor...");
                    Thread.sleep(1000);
                    break;
                } else {
                    System.out.println("Odeyeceginiz tutari giriniz (" + ucret + " TL)");
                    double odeme = input.nextDouble();

                    if (odeme < 0) {
                        System.out.println("Lutfen gecerli bir sayi giriniz.");
                        Thread.sleep(1000);
                    } else if (odeme == 0) {

                        return;
                    } else {
                        paraUstu = odeme - ucret;
                        if (paraUstu >= 0) {
                            fiyatStok[numara - 1][1]--;
                            urunKaydet();
                            System.out.println("Siparis basarili! Para ustu : " + paraUstu);
                            Thread.sleep(500);

                            boolean yeniSiparis = false;
                            while (true) {
                                System.out.println("Tekrar siparis olusturmak ister misiniz?");
                                System.out.println("0 - Bitir");
                                System.out.println("1 - Devam");
                                int devam = input.nextInt();

                                if (devam == 1) {
                                    yeniSiparis = true;
                                    break;
                                } else if (devam == 0) {
                                    System.out.println("Ana menuye donuluyor...");
                                    Thread.sleep(1500);
                                    return;
                                } else {
                                    System.out.println("Yanlis Tuslama!");
                                    Thread.sleep(1000);
                                    deneme++;
                                    if (deneme >= 3) {
                                        System.out.println("Ardarda yanlis tuslama, Basa donuluyor...");
                                        Thread.sleep(1500);
                                        return;
                                    }
                                }
                            }

                            if (yeniSiparis) {
                                break;
                            }

                        } else {
                            paraUstu = ucret - odeme;
                            System.out.println("Bakiye Yetersiz! " + paraUstu + " TL daha gerek.");
                            Thread.sleep(1000);
                        }
                    }
                }
            }
        }
    }

    static void varsayilanList() throws Exception{
        urunSayisi = 0;
        File urunIsmi = new File("data\\urunIsmi.txt");
        Scanner isim = new Scanner(urunIsmi);
        File urunFiyat = new File("data\\urunFiyat.txt");
        Scanner fiyat = new Scanner(urunFiyat);
        File urunStok = new File("data\\urunStok.txt");
        Scanner stok = new Scanner(urunStok);
        while(isim.hasNextLine() == true){
            urunler[urunSayisi] = isim.nextLine();
            fiyatStok[urunSayisi][0] = fiyat.nextInt(); 
            fiyatStok[urunSayisi][1] = stok.nextInt();
            urunSayisi++;
        }
        File kayitliSifre = new File("data\\sifre.txt");
        Scanner sifre = new Scanner(kayitliSifre);
        password = sifre.nextLine(); 
        isim.close();
        fiyat.close();
        stok.close();
        sifre.close();
    }

    static void yiyecekList() {
        for (int i = 0; i < urunSayisi; i++) {
            System.out.println(
                    (i + 1) + " - " + urunler[i] + "  \t" + fiyatStok[i][0] + " TL" + "\tSTOK = " + fiyatStok[i][1]);
        }

    }

    static int fiyatBul(int x) {
        if (x > urunSayisi || x <= 0) {
            return -1;
        } else {
            return fiyatStok[x - 1][0];
        }

    }

    static int adminDogrulama()throws Exception {
        while (true) {
            System.out.println("Sifreyi giriniz. Sifirlamak icin 1'i, geri donmek icin 0'i tuslayiniz.");
            String sifre = input.nextLine();
            if (sifre.equals(password)) {
                System.out.println("Giris Basarili!");
                Thread.sleep(1000);
                return 1;
            } else if (sifre.equals("1")) {
                sifreSifirlama();
            } else if (sifre.equals("0")) {
                return 0;
            } else {
                System.out.println("Yanlis sifre! tekrar deneyiniz.");
                Thread.sleep(1000);
            }
        }
    }

    static void sifreSifirlama() throws Exception{
        while (true) {
            System.out.println("Yeni sifreyi giriniz. Geri donus icin 0'a basiniz.");
            String sifre = input.nextLine();
            if (sifre.equals("0")) {
                return;
            }
            System.out.println("Sifreyi tekrar giriniz. Geri donus icin 0'a basiniz.");
            String sifre2 = input.nextLine();
            if (sifre2.equals("0")) {
                return;
            } else if (sifre2.equals(sifre)) {
                password = sifre;
                System.out.println("Sifirlama basarili!");
                Thread.sleep(1000);
                return;
            } else {
                System.out.println("Girilen sifreler uyusmuyor! Lutfen tekrar deneyiniz.");
                Thread.sleep(1000);
            }

        }

    }

    static void adminPanel() throws Exception{
        int secim;
        while (true) {
            System.out.println("0 - Cikis");
            System.out.println("1 - Urun ekle");
            System.out.println("2 - Urun ve Fiyat Duzenle");
            System.out.println("3 - Urun kaldir");
            System.out.println("4 - Sifreyi Degistir");
            System.out.print("Yapacaginiz islemi seciniz: ");
            secim = input.nextInt();
            input.nextLine();
            switch (secim) {
                case 0:
                    return;
                case 1:
                    urunEkleme();
                    break;
                case 2:
                    urunDuzenle();
                    break;
                case 3:
                    urunKaldir();
                    break;
                case 4:
                    sifreSifirlama();
                    break;
                default:
                    System.out.println("Gecerli bir sayi giriniz.");
                    Thread.sleep(1000);
            }
        }
    }

    static void urunEkleme() throws Exception{
        yiyecekList();
        while (true) {
            System.out.print("Urun ismini girin: ");
            String urunIsmi = input.nextLine();
            input.nextLine();
            if (urunIsmi.equals("0")) {
                return;
            }
            System.out.print("Urun Fiyatini girin: ");
            int urunFiyat = input.nextInt();
            input.nextLine();
            if (urunFiyat == 0) {
                return;
            } else if (urunFiyat < 0) {
                System.out.println("Gecerli bir fiyat giriniz.");
                Thread.sleep(1000);
                continue;
            }
            System.out.print("Urun stogu girin: ");
            int urunStok = input.nextInt();
            input.nextLine();
            if (urunStok < 0) {
                System.out.println("Gecerli bir stok giriniz.");
                Thread.sleep(1000);
                continue;
            }
            if (urunSayisi < 40) {
                urunler[urunSayisi] = urunIsmi;
                fiyatStok[urunSayisi][0] = urunFiyat;
                fiyatStok[urunSayisi][1] = urunStok;
                urunSayisi++;
                urunKaydet();
                System.out.println("Urun ekleme basarili!");
                Thread.sleep(1000);
                return;
            } else {
                System.out.println("Makine dolu!");
                Thread.sleep(1000);
            }

        }
    }

    static void urunDuzenle() throws Exception{
        while (true) {
            yiyecekList();
            System.out.println("Islem yapilacak urunu seciniz.");
            int urunSecim = input.nextInt();
            if (urunSecim == 0) {
                return;
            } else if (urunSecim > urunSayisi || urunSecim < 0) {
                System.out.println("Urun bulunamadi! Lutfen tekrar deneyiniz.");
                Thread.sleep(1000);
                continue;
            } else {
                System.out.println("1 - Urunu degistir");
                System.out.println("2 - Urun Fiyati degistir");
                System.out.println("3 - Urun Stogu guncelle");
                System.out.println("Yapilacak islemi seciniz.");
                int secim = input.nextInt();
                input.nextLine();
                if (secim == 0) {
                    return;
                } else if (secim > 3 || secim < 0) {
                    System.out.println("Lutfen gecerli bir islem seciniz!");
                    Thread.sleep(1000);
                    continue;
                } else if (secim == 1) {
                    System.out.print("Yeni urunu giriniz: ");
                    String isim = input.nextLine();
                    if (isim.equals("0")) {
                        return;
                    } else {
                        urunler[urunSecim - 1] = isim;
                        urunKaydet();
                        System.out.println("Islem Basarili!");
                        Thread.sleep(1000);
                    }
                } else if (secim == 2) {
                    System.out.print("Urun Fiyati giriniz: ");
                    int yeniFiyat = input.nextInt();
                    if (yeniFiyat == 0) {
                        return;
                    } else {
                        fiyatStok[urunSecim - 1][0] = yeniFiyat;
                        urunKaydet();
                        System.out.println("Islem Basarili!");
                        Thread.sleep(1000);
                    }
                } else {
                    System.out.print("Urun stok miktarini giriniz: ");
                    int yeniStok = input.nextInt();
                    if (yeniStok == 0) {
                        return;
                    } else {
                        fiyatStok[urunSecim - 1][1] = yeniStok;
                        urunKaydet();
                        System.out.println("Islem Basarili!");
                        Thread.sleep(1000);
                    }
                }
            }
        }
    }

    static void urunKaldir() throws Exception{
        int deneme = 0;
        while (true) {
            yiyecekList();
            System.out.print("Kaldirmak istediginiz urunu seciniz: ");
            int secim = input.nextInt();
            if (secim == 0) {
                return;
            } else if (secim < 0 || secim > urunSayisi) {
                System.out.println("Lutfen gecerli bir urun seciniz!");
                Thread.sleep(1000);
                continue;
            } else {
                while (true) {
                    System.out.println(urunler[secim - 1] + " adli urunu kaldirmaya emin misiniz?");
                    System.out.println("0 - Geri Don");
                    System.out.println("1 - Devam");
                    int devam2 = input.nextInt();
                    if (devam2 == 0) {
                        break;
                    } else if (devam2 != 1) {
                        System.out.println("Yanlis Tuslama!");
                        Thread.sleep(1000);
                        deneme++;
                        if (deneme >= 3) {
                            System.out.println("Ardarda yanlis tuslama, Basa donuluyor...");
                            Thread.sleep(1000);
                            return;
                        }
                        continue;

                    } else {
                        for (int i = secim - 1; i < urunSayisi - 1; i++) {
                            urunler[i] = urunler[i + 1];
                            fiyatStok[i][0] = fiyatStok[i + 1][0];
                            fiyatStok[i][1] = fiyatStok[i + 1][1];
                        }
                        urunler[urunSayisi - 1] = null;
                        fiyatStok[urunSayisi - 1][0] = 0;
                        fiyatStok[urunSayisi - 1][1] = 0;
                        urunSayisi--;
                        System.out.println("Islem Basarili!");
                        urunKaydet();
                        break;
                    }
                }
            }
        }
    }

    static void urunKaydet() throws Exception{
        FileWriter urunIsmi = new FileWriter("data\\urunIsmi.txt");
        FileWriter urunStok = new FileWriter("data\\urunStok.txt");
        FileWriter urunFiyat = new FileWriter("data\\urunFiyat.txt");
        for(int i = 0; i < urunSayisi; i++){
            urunIsmi.write(urunler[i]+ "\n"); 
            urunFiyat.write(fiyatStok[i][0]+"\n");
            urunStok.write(fiyatStok[i][1]+"\n");
        }
        FileWriter sifreKaydet = new FileWriter("data\\sifre.txt");
        sifreKaydet.write(password);
        urunIsmi.close();
        urunStok.close();
        urunFiyat.close();
        sifreKaydet.close();
    }
}