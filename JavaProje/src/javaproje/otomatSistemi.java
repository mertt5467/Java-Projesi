package javaproje;

import java.util.Scanner;

public class otomatSistemi {

    static String password = "yeniYuzyil";
    static String[] urunler = new String[40]; //Otomata koyulabilecek maksimum urun sayisi 40'tir.
    static int[] fiyat = new int[40];
    static int[] stok = new int[40];
    static int urunSayisi = 0;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        varsayilanList();
        while (true) {
            System.out.println("\n--- ANA MENU ---");
            System.out.println("1 - Siparis");
            System.out.println("2 - Admin Paneli");
            System.out.println("3 - Programi bitir");
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
                System.out.println("Bitiriliyor...");
                break;
            } else {
                System.out.println("Lutfen gecerli bir sayi giriniz.");
            }
        }
    }

    static void kullanici() {
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
                continue;
            } else {
                if (stok[numara-1] > 0) {
                    stok[numara-1]--;
                    break;
                } else {
                    System.out.println("Sectiginiz urunun stogu kalmamistir.");
                    continue;
                }

            }
        }
        while (true) {
            int ucret = fiyatBul(numara);

            if (ucret == -1) {
                System.out.println("Gecersiz sayi, basa donuluyor...");
                break;
            } else {
                System.out.println("Odeyeceginiz tutari giriniz");
                double odeme = input.nextDouble();
                if (odeme < 0) {
                    System.out.println("Lutfen gecerli bir sayi giriniz.");
                } else if (odeme == 0) {
                    return;
                } else {
                    paraUstu = odeme - ucret;
                    if (paraUstu >= 0) {
                        System.out.println("Siparis basarili! Para ustu : " + paraUstu);
                        System.out.println("Basa donuluyor...");
                        break;
                    } else {
                        paraUstu = ucret - odeme;
                        System.out.println("Bakiye Yetersiz! " + paraUstu + " TL daha gerek.");
                    }
                }
            }
        }
    }

    static void varsayilanList() {
        urunSayisi = 0;
        urunler[0] = "Su";
        fiyat[0] = 10;
        stok[0] = 10;
        urunSayisi++;
        urunler[1] = "Kraker";
        fiyat[1] = 15;
        stok[1] = 5;
        urunSayisi++;
        urunler[2] = "Cikolata";
        fiyat[2] = 20;
        stok[2] = 5;
        urunSayisi++;
        urunler[3] = "Soda";
        fiyat[3] = 20;
        stok[3] = 5;
        urunSayisi++;
        urunler[4] = "Kek";
        fiyat[4] = 18;
        stok[4] = 5;
        urunSayisi++;
        urunler[5] = "Biskuvi";
        fiyat[5] = 25;
        stok[5] = 5;
        urunSayisi++;
    }

    static void yiyecekList() {
        for (int i = 0; i < urunSayisi; i++) {
            System.out.println((i + 1) + " - " + urunler[i] + "  \t" + fiyat[i] + " TL" + "\tSTOK = " + stok[i]);
        }

    }

    static int fiyatBul(int x) {
        if (x > urunSayisi || x <= 0) {
            return -1;
        } else {
            return fiyat[x - 1];
        }

    }

    static int adminDogrulama() {
        while (true) {
            System.out.println("Sifreyi giriniz. Sifirlamak icin 1'i, geri donmek icin 0'i tuslayiniz.");
            String sifre = input.nextLine();
            if (sifre.equals(password)) {
                System.out.println("Giris Basarili!");
                return 1;
            } else if (sifre.equals("1")) {
                sifreSifirlama();
            } else if (sifre.equals("0")) {
                return 0;
            } else {
                System.out.println("Yanlis sifre! tekrar deneyiniz.");
            }
        }
    }

    static void sifreSifirlama() {
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
                return;
            } else {
                System.out.println("Girilen sifreler uyusmuyor! Lutfen tekrar deneyiniz.");
            }

        }

    }

    static void adminPanel() {
        int secim;
        while (true) {
            System.out.println("0 - Cikis");
            System.out.println("1 - Urun ekle");
            System.out.println("2 - Urun ve Fiyat Duzenle");
            System.out.println("3 - Sifreyi Degistir");
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
                    sifreSifirlama();
                    break;
                default:
                    System.out.println("Gecerli bir sayi giriniz.");
            }
        }
    }

    static void urunEkleme() {
        yiyecekList();
        while (true) {
            System.out.print("Urun ismini girin: ");
            String urunIsmi = input.nextLine();
            if (urunIsmi.equals("0")) {
                return;
            }
            System.out.print("Urun Fiyatini girin: ");
            int urunFiyat = input.nextInt();
            if (urunFiyat == 0) {
                return;
            } else if (urunFiyat < 0) {
                System.out.println("Gecerli bir fiyat giriniz.");
                continue;
            }
            System.out.print("Urun stogu girin: ");
            int urunStok = input.nextInt();
            if (urunStok == 0) {
                return;
            } else if (urunStok < 0) {
                System.out.println("Gecerli bir stok giriniz.");
                continue;
            }
            if (urunSayisi < 40) {
                urunler[urunSayisi] = urunIsmi;
                fiyat[urunSayisi] = urunFiyat;
                stok[urunSayisi] = urunStok;
                urunSayisi++;
                System.out.println("Urun ekleme basarili!");
                return;
            } else {
                System.out.println("Makine dolu!");
            }

        }
    }

    static void urunDuzenle() {
        while (true) {
            yiyecekList();
            System.out.println("Islem yapilacak urunu seciniz.");
            int urunSecim = input.nextInt();
            if (urunSecim == 0) {
                return;
            } else if (urunSecim > urunSayisi || urunSecim < 0) {
                System.out.println("Urun bulunamadi! Lutfen tekrar deneyiniz.");
                continue;
            } else {
                System.out.println("1 - Urunu degistir \n2 - Urun Fiyati degistir\n3 - Urun Stogu guncelle \nYapilacak islemi seciniz.");
                int secim = input.nextInt();
                input.nextLine();
                if (secim == 0) {
                    return;
                } else if (secim > 3 || secim < 0) {
                    System.out.println("Lutfen gecerli bir islem seciniz!");
                    continue;
                } else if (secim == 1) {
                    System.out.print("Yeni urunu giriniz: ");
                    String isim = input.nextLine();
                    if (isim.equals("0")) {
                        return;
                    } else {
                        urunler[urunSecim - 1] = isim;
                        System.out.println("Islem Basarili!");
                    }
                } else if (secim == 2) {
                    System.out.print("Urun Fiyati giriniz: ");
                    int yeniFiyat = input.nextInt();
                    if (yeniFiyat == 0) {
                        return;
                    } else {
                        fiyat[urunSecim - 1] = yeniFiyat;
                        System.out.println("Islem Basarili!");
                    }
                } else {
                    System.out.print("Urun stok miktarini giriniz: ");
                    int yeniStok = input.nextInt();
                    if (yeniStok == 0) {
                        return;
                    } else {
                        stok[urunSecim - 1] = yeniStok;
                        System.out.println("Islem Basarili!");

                    }
                }
            }
        }
    }
}
