package laptrinhandroid.fpoly.dnnhm3.Fragment.Entity;

public class ThongBaoAdmin {
     private int id,maNV;
    private String ngay;
    private Boolean doc;
//recyclerView.findViewHolderForAdapterPosition(0).itemView.performClick()

     public ThongBaoAdmin(int maNV, String ngay, Boolean doc) {
         this.maNV = maNV;
         this.ngay = ngay;
         this.doc = doc;
     }

     public ThongBaoAdmin(int id, int maNV, String ngay, Boolean doc) {
         this.id = id;
         this.maNV = maNV;
         this.ngay = ngay;
         this.doc = doc;
     }

     public Boolean getDoc() {
         return doc;
     }

     public void setDoc(Boolean doc) {
         this.doc = doc;
     }

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }


}
