#ifndef COMMUNITY_GARDEN_H
#define COMMUNITY_GARDEN_H

#include <vector>
#include <string>
#include <ctime>  // for created_at attribute

class GardenEntity {
 public:
  std::string location;
  std::time_t created_at;
  virtual void initialize() = 0;
  virtual void destroy() = 0;
};

class GardenPlot : public GardenEntity {
 public:
  int size;
  int soil_quality;
  std::vector<std::string> planted_crops;

  void plant(const std::string& crop);
  void harvest();
  void fertilize();
};

class GardenEvent : public GardenEntity {
 public:
  std::string event_type;
  std::vector<std::string> participants;
  std::time_t date;

  void schedule(std::time_t event_date);
  void conduct();
  void award();
};

class GardenResource : public GardenEntity {
 public:
  std::string resource_type;
  int quantity;

  void collect();
  void use();
};

class CommunityGarden {
 private:
  std::vector<GardenPlot> garden_plots;
  std::vector<GardenEvent> garden_events;
  std::vector<GardenResource> garden_resources;

 public:
  void add_plot(const GardenPlot& plot);
  void remove_plot(int plot_index);
  void schedule_event(const GardenEvent& event);
  void collect_resource(const GardenResource& resource);
};

#endif  // COMMUNITY_GARDEN_H
