//package com.example.myproject.domain.file;
//
//import org.springframework.web.multipart.MultipartFile;
//
//public class S3FileStorageService implements FileService {
//
//    private final S3Client s3Client;
//    private final String bucketName;
//
//    @Autowired
//    public S3FileStorageService(@Value("${aws.s3.bucketName}") String bucketName,
//                                @Value("${aws.region}") String region) {
//        this.s3Client = S3Client.builder().region(Region.of(region)).build();
//        this.bucketName = bucketName;
//    }
//
//
//    @Override
//    public String uploadFile(MultipartFile file) {
//        String fileName = file.getOriginalFilename();
//        try {
//            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(fileName)
//                    .build();
//            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
//            return fileName;
//        } catch (IOException e) {
//            throw new RuntimeException("Could not store file " + fileName, e);
//        }
//    }
//
//    @Override
//    public byte[] downloadFile(String fileKey) {
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                .bucket(bucketName)
//                .key(fileName)
//                .build();
//        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
//        return objectBytes.asByteArray();
//    }
//}
