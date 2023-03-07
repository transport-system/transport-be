package com.transport.transport.service.impl.file;


import org.springframework.stereotype.Service;


@Service
public class FileServiceImp {
//  //  @Value("${firebase.image-url}")
//    private String imageUrl;
//
//    @Override
//    public String getImageUrl(String name) {
//        return String.format(imageUrl, name);
//    }
//
//    @Override
//    public String save(MultipartFile file) throws IOException {
//
//        Bucket bucket = StorageClient.getInstance().bucket();
//
//        String name = generateFileName(file.getOriginalFilename());
//
//        bucket.create(name, file.getBytes(), file.getContentType());
//
//        return name;
//    }
//
//    @Override
//    public String save(BufferedImage bufferedImage, String originalFileName) throws IOException {
//
//        byte[] bytes = getByteArrays(bufferedImage, getExtension(originalFileName));
//
//        Bucket bucket = StorageClient.getInstance().bucket();
//
//        String name = generateFileName(originalFileName);
//
//        bucket.create(name, bytes);
//
//        return name;
//    }
//
//    @Override
//    public void delete(String name) throws IOException {
//
//        Bucket bucket = StorageClient.getInstance().bucket();
//
//        if (StringUtils.isEmpty(name)) {
//            throw new IOException("invalid file name");
//        }
//
//        Blob blob = bucket.get(name);
//
//        if (blob == null) {
//            throw new IOException("file not found");
//        }
//        blob.delete();
//    }
}
