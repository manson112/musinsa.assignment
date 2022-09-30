insert into menu(menu_title, menu_link, sort_order, create_at, update_at) values ('상의', 'https://dummylink.xyz', 0, now(), now());
insert into menu(menu_title, menu_link, sort_order, create_at, update_at) values ('아우터', 'https://dummylink.xyz', 1, now(), now());
insert into menu(menu_title, menu_link, sort_order, create_at, update_at) values ('바지', 'https://dummylink.xyz', 2, now(), now());
insert into menu(menu_title, menu_link, sort_order, create_at, update_at) values ('원피스', 'https://dummylink.xyz', 3, now(), now());
insert into menu(menu_title, menu_link, sort_order, create_at, update_at) values ('스커트', 'https://dummylink.xyz', 4, now(), now());
insert into menu(menu_title, menu_link, sort_order, create_at, update_at) values ('스니커즈', 'https://dummylink.xyz', 5, now(), now());


insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('반소매 티셔츠', 'https://dummylink.xyz', 0, 1, now(), now()); -- 7
insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('피케/카라 티셔츠', 'https://dummylink.xyz', 1, 1, now(), now());

insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('긴소매 티셔츠', 'https://dummylink.xyz', 2, 2, now(), now());
insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('맨투맨/스웨트셔츠', 'https://dummylink.xyz', 3, 2, now(), now());

insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('민소매 티셔츠', 'https://dummylink.xyz', 4, 3, now(), now());
insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('후드 티셔츠', 'https://dummylink.xyz', 5, 3, now(), now());

insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('셔츠/블라우스', 'https://dummylink.xyz', 6, 4, now(), now());
insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('니트/스웨터', 'https://dummylink.xyz', 7, 5, now(), now());

insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('기타 상의', 'https://dummylink.xyz', 8, 7, now(), now()); -- 15
insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('후드 집업', 'https://dummylink.xyz', 0, 15, now(), now());
insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('블루종/MA-1', 'https://dummylink.xyz', 1, 16, now(), now());
insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('레더/라이더스 재킷', 'https://dummylink.xyz', 2, 17, now(), now());
insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('무스탕/', 'https://dummylink.xyz', 3, 18, now(), now());
insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('트러커 재킷', 'https://dummylink.xyz', 4, 13, now(), now());
insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('슈트/블레이저 재킷', 'https://dummylink.xyz', 5, 12, now(), now());
insert into menu(menu_title, menu_link, sort_order, parent_menu_id, create_at, update_at) values ('카디건', 'https://dummylink.xyz', 6, 12, now(), now());

-- BANNER
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink.co.kr', 'https://imageLink.com', 3);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink1.co.kr', 'https://imageLink.com', 3);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink2.co.kr', 'https://imageLink.com', 3);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink3.co.kr', 'https://imageLink.com', 3);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink4.co.kr', 'https://imageLink.com', 3);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink5.co.kr', 'https://imageLink.com', 3);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink6.co.kr', 'https://imageLink.com', 3);

insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink7.co.kr', 'https://imageLink.com', 4);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink8.co.kr', 'https://imageLink.com', 4);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink9.co.kr', 'https://imageLink.com', 5);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink10.co.kr', 'https://imageLink.com', 5);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink11.co.kr', 'https://imageLink.com', 5);

insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink14.co.kr', 'https://imageLink.com', 1);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink15.co.kr', 'https://imageLink.com', 1);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink16.co.kr', 'https://imageLink.com', 1);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink17.co.kr', 'https://imageLink.com', 1);
insert into banner(banner_link, banner_image_url, menu_id) values ('https://bannerLink18.co.kr', 'https://imageLink.com', 1);