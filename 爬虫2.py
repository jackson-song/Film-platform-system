# XPath 尝试爬取豆瓣tag
from lxml import etree
import traceback
import requests
import time
import random
import pymysql


# 定义book对象
class book: 
    isbn = 0


    def __init__(self) :
        print("无参构造")
    # print()

    def myinit(self,isbn, imgurl, bookname, pubinfo, rating, ratnum, synopsis):
        self.isbn = isbn
        self.imgurl = imgurl
        self.bookname = bookname
        self.author = pubinfo[0]
        self.press = pubinfo[1]
        self.publicationtime = pubinfo[2]
        self.bookgrading = float(rating)
        self.ratnum = int(ratnum)
        self.synopsis = synopsis

books = []


# tags = ['小说', '随笔', '旅行', '散文', '科幻', '历史', '互联网', '青春', '言情', '名著'] #原 言情不行,部分书籍没有出版社 会数据错误
tags = ['小说', '随笔', '旅行', '散文', '科幻', '历史', '互联网', '青春', '名著', '日本文学'] # 可
# tags = ['名著'] #测试
"""   """
# 标签循环
for tag in tags:
# 页面循环
    for page in range(1, 2):
        resp = requests.get(
            url=f'https://book.douban.com/tag/{tag}?start={(page - 1) * 20}',
            headers={'User-Agent': 'BaiduSpider'}
        )
        tree = etree.HTML(resp.text)
        # 通过XPath语法从页面中提取电影标题
        # title_spans = tree.xpath('//*[@id="content"]/div/div[1]/ol/li/div/div[2]/div[1]/a/span[1]')
        # # 通过XPath语法从页面中提取电影评分
        # rank_spans = tree.xpath('//*[@id="content"]/div/div[1]/ol/li/div/div[2]/div[2]/div/span[2]')

        # 详情页url获取
        detailusrls = tree.xpath('//*[@class="subject-item"]/div[2]/h2/a/@href')
        # print(detailusrls)

        isbns = []
        ratings = []
        synopses = []

        for detailusrl in detailusrls:
            # break
            # print(detailusrl)
            tresp = requests.get(
                url=detailusrl,
                headers={'User-Agent': 'BaiduSpider'}
            )
            ttree = etree.HTML(tresp.text)

            isbn = ttree.xpath('//*[@class="subject clearfix"]/div[2]/text()')
            print(isbn[-2].strip())

            # imgurl = ttree.xpath('//*[@class="nbg"]/@href')[0]
            # print(imgurl)

            # bookname = ttree.xpath('//*[@class="nbg"]/@title[1]')[0]
            # print(bookname)

            # author = ttree.xpath('//*[@id="info"]/span[1]/a/text()')
            # print(author)

            rating = ttree.xpath('//*[@class="ll rating_num "]/text()')[0]
            print(rating)
            ratings.append(rating)

            # synopsis = ttree.xpath('//*[@class="intro"]/p/text()')
            synopsis = ttree.xpath('//*[@class="intro"]/p/text()')[0]
            print(synopsis)
            # str = ""
            # for s in synopsis :
            #     str += s
            #     print(str)
            # synopsis = str
            synopsis.strip()
            print(synopsis)
            synopses.append(synopsis)

            isbns.append(isbn[-2].strip())

            time.sleep(random.random() * 4 + 1)

        print("isbn数:")
        print(len(isbns))
        # break

        # 图片url
        img_urls = tree.xpath('//*[@class="subject-item"]/div[1]/a/img/@src')
        print("图片数:")
        print(len(img_urls))
        # 书名
        booknames = tree.xpath('//*[@class="subject-item"]/div[2]/h2/a/text()')

        # 作者和出版信息
        pubinfos = tree.xpath('//*[@class="subject-item"]/div[2]/div[1]/text()')
        print("info数:")
        print(len(pubinfos))

        # 评分
        # ratings = tree.xpath('//*[@class="subject-item"]/div[2]/div[2]/span[2]/text()')
        print("评价数:")
        print(len(ratings))
        print(ratings)

        # 评价数
        ratnums = tree.xpath('//*[@class="subject-item"]/div[2]/div[2]//*[@class="pl"]/text()')
        print("评价数-数:")
        print(len(ratnums))
        print(ratnums)

        # 简介
        # synopses = tree.xpath('//*[@class="subject-item"]/div[2]/p[1]/text()')
        print("简介数:")
        print(len(synopses))

        # for title_span, rank_span in zip(title_spans, rank_spans):
        #     print(title_span.text, rank_span.text)

        count = 0


        # 循环遍历图片url
        # for img_url in img_urls:
        #     # print(img_url)
        #     count = count + 1

        # print(count)
        # count = 0

        # 循环遍历书名,strip()去掉前后空格
        # for bookname in booknames:
        #     bookname = bookname.strip()
        #     print(bookname)
        #     count = count + 1
        # print(booknames)
        # print(count)
        # count = 0

        # 多余空白内容处理
        tbooknames = []
        for bookname in booknames:
            bookname = bookname.strip()
            if bookname != '' :
                tbooknames.append(bookname)
                # print(bookname)
        booknames = tbooknames
        print("书名数:")
        print(len(booknames))

        # for i in range(len(booknames)):
        #     booknames[i] = booknames[i].strip()
        #     if booknames[i] == '':
        #         del booknames[i]
        # print(booknames)


        # 遍历当前页所有书籍出版信息,删除翻译书籍的译者信息
        infolists = []
        for pubinfo in pubinfos:
            # print(pubinfo.strip())

            # 使用strip()去掉前后空白符号,使用split对原数据进行切割 返回list
            infolist = pubinfo.strip().split('/')
            # print(infolist)


            # 删掉译者信息
            if len(infolist) == 5 :
                del infolist[1]

            # 去掉多余的空格
            for i in range(len(infolist)):
                infolist[i] = infolist[i].strip()
            # print(infolist)

            # 此是infolist的值分别是
            # [0]作者 [1]出版社 [2]出版时间 [3]价格
            # print(infolist)
            infolists.append(infolist)
            # count = count + 1
        print(len(infolists))
        # print(count)
        # count = 0

        # 所有书籍评分
        # for rating in ratings :
        #     print(rating)
            # count = count + 1

        print(len(ratings))
        # print(count)
        # count = 0

        # 所有书籍评分 需提取出纯数字
        tratnums = []
        for ratnum in ratnums:
            # print(ratnum.strip())
            # count = count + 1
            tmp = ""
            # 提取字符串中的数字
            for i in range(len(ratnum)):
                if(ratnum[i] >= '0' and ratnum[i] <= '9'):
                   tmp = tmp + ratnum[i]
            # print(tmp)
            tratnums.append(tmp)

        # print(tratnums)
        ratnums = tratnums
        print(ratnums)
        # print(count)
        count = 0

        # 所有书籍简介
        # for synopsis in synopses:
        #     print((synopsis))
        #     count = count + 1

        # print(count)

        # 数据库连接
        conn = pymysql.connect(host="localhost",port=3306,user="root",password="123",db="teaching",charset='utf8')



        # sql = "insert into book(bookname,isbn,imgurl,author,press,publicationtime,bookgrading,ratnum,synopsis) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)" % (tmpbook.bookname,tmpbook.isbn,tmpbook.img_url,tmpbook.author,tmpbook.press,tmpbook.publicationtime,tmpbook.bookgrading,tmpbook.ratnum,tmpbook.synopsis})
        # tmpbook = book(1234567890, "imgurl", "bookname", ["infolist0","出版社","出版时间"], 9.9, 99, "synopsis")
        # print(tmpbook.bookname, tmpbook.isbn, tmpbook.imgurl, tmpbook.author, tmpbook.press, tmpbook.publicationtime,
        # tmpbook.bookgrading, tmpbook.ratnum, tmpbook.synopsis)
        # sql = "insert into book(bookname,isbn,imgurl,author,press,publicationtime,bookgrading,ratnum,synopsis) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)" % (
        # tmpbook.bookname, tmpbook.isbn, tmpbook.imgurl, tmpbook.author, tmpbook.press, tmpbook.publicationtime,
        # tmpbook.bookgrading, tmpbook.ratnum, tmpbook.synopsis)
        # (bookname,isbn,imgurl,author,press,publicationtime,bookgrading,ratnum,synopsis)

        # sql = "insert into book values(%(bookname)s,%(isbn)s,%(imgurl)s,%(author)s,%(press)s,%(publicationtime)s,%(bookgrading)s,%(ratnum)s,%(synopsis)s)"
        # print(sql,{'bookname':tmpbook.bookname, 'isbn':tmpbook.isbn , 'imgurl':tmpbook.imgurl, 'author':tmpbook.author, 'press':tmpbook.press,
        #            'publicationtime':tmpbook.publicationtime,'bookgrading':tmpbook.bookgrading,'ratnum':tmpbook.ratnum,'synopsis':tmpbook.synopsis})
        # try:
        #     # 执行sql语句
        #     cursor.execute(sql, {'bookname':tmpbook.bookname, 'isbn':tmpbook.isbn , 'imgurl':tmpbook.imgurl, 'author':tmpbook.author, 'press':tmpbook.press,
        #            'publicationtime':tmpbook.publicationtime,'bookgrading':tmpbook.bookgrading,'ratnum':tmpbook.ratnum,'synopsis':tmpbook.synopsis})
        #     # 提交到数据库执行
        #     conn.commit()
        # except:
        #     # Rollback in case there is any error
        #     conn.rollback()
        #     print("非正常结束")
        # conn.close()
    # time.sleep(random.random() * 4 + 2)
        # count = 0
        # 最后对数据进行整合
        print(len(isbns) + len(img_urls) + len(booknames) + len(infolists) + len(ratings) + len(ratnums) + len(synopses))
        for isbn, img_url, bookname, infolist, rating, ratnum, synopsis in zip(isbns, img_urls, booknames, infolists, ratings, ratnums, synopses):

            print(isbn + img_url + bookname + infolist[0] + infolist[1] + infolist[2] + rating + ratnum + synopsis)
            # tmpbook = book(isbn, img_url, bookname, infolist, rating, ratnum, synopsis)
            tmpbook = book()
            tmpbook.isbn = isbn
            tmpbook.imgurl = img_url
            tmpbook.bookname = bookname
            tmpbook.author = infolist[0]
            tmpbook.press = infolist[1]
            tmpbook.publicationtime = infolist[2]
            if(rating.strip() == '') :
                tmpbook.bookgrading = 0.0
            else:
                tmpbook.bookgrading = rating.strip()
            if tmpbook.bookgrading == 0.0 and ratnum == 10 :
                tmpbook.ratnum = 0
            else:
                tmpbook.ratnum = ratnum
            tmpbook.synopsis = synopsis.strip()
            print(f'"书名:" + {tmpbook.bookname} + "isbn:" + {tmpbook.isbn} + "tupianurl:" + {tmpbook.imgurl} + "作者:" + '
                  f'{tmpbook.author} + "出版社:" + {tmpbook.press} + "出版时间:" + {tmpbook.publicationtime} + "评分:" + {tmpbook.bookgrading} '
                  f'+ "评价次数:" + {tmpbook.ratnum} + "简介": + {tmpbook.synopsis}')


            cursor = conn.cursor()
            sql = "insert into book values(%(bookname)s,%(isbn)s,%(imgurl)s,%(author)s,%(press)s,%(publicationtime)s,%(bookgrading)s,%(ratnum)s,%(synopsis)s)"

            try:
                cursor.execute(sql, {'bookname': tmpbook.bookname, 'isbn': tmpbook.isbn, 'imgurl': tmpbook.imgurl,
                                 'author': tmpbook.author, 'press': tmpbook.press,
                                 'publicationtime': tmpbook.publicationtime, 'bookgrading': tmpbook.bookgrading,
                                 'ratnum': tmpbook.ratnum, 'synopsis': tmpbook.synopsis})
            #     提交到数据库执行
                conn.commit()
            except(Exception, BaseException) as e:
                print('{:*^60}'.format('直接打印出e, 输出错误具体原因'))
                print(e)
                print('{:*^60}'.format('使用repr打印出e, 带有错误类型'))
                print(repr(e))
                print('{:*^60}'.format('使用traceback的format_exc可以输出错误具体位置'))
                exstr = traceback.format_exc()
                # Rollback in case there is any error
                conn.rollback()
                print("非正常结束")

            # sql = "insert into book(bookname,isbn,imgurl,author,press,publicationtime,bookgrading,ratnum,synopsis) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)" % (
            # tmpbook.bookname,tmpbook.isbn,tmpbook.imgurl,tmpbook.author,tmpbook.press,tmpbook.publicationtime,
            # tmpbook.bookgrading,tmpbook.ratnum,tmpbook.synopsis)
            # tmptxt = f'{tmpbook.bookname}+{tmpbook.isbn}+{tmpbook.imgurl}+{tmpbook.author}+{tmpbook.press}+{tmpbook.publicationtime}+{tmpbook.bookgrading}+{tmpbook.ratnum}+{tmpbook.synopsis}$\n'
            # with open("outtest.txt","a",encoding="utf-8") as file:
            #   file.write(tmptxt)

            # cursor.close()
            books.append(tmpbook)
        print("总爬取书籍数(正常情况为20 * tag数 * page数(page范围左闭右开)):")
        print(len(books))
        # conn.close()

        # i = 0
        # print(len(isbns))
        # print(len(img_urls))
        # for isbn,img_url in zip(isbns,img_urls):
        #     print(i)
        #     i = i + 1
        #     print(isbn + img_url)

        time.sleep(random.random() * 4 + 4)
