create table algo_configuration
(
  id            int     primary key,
  version       int,
  steps         json    not null,
  author        text    default 'UNSPECIFIED',
  task          text    default 'UNSPECIFIED',
  description   text    default 'UNSPECIFIED'
);